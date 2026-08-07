package com.awrdev.white_list_tester.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.awrdev.white_list_tester.ResourcesList
import com.awrdev.white_list_tester.WebResource
import com.awrdev.white_list_tester.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ConnectivityTester {
    suspend fun testGivenList(context: Context, givenList: List<WebResource>): String{
        return withContext(Dispatchers.IO){
        var successTries = 0
        var failedTries = 0
        var networkSleepDetected = false
        for (resource in givenList){

            if (!isSystemNetworkAvailable(context)) {
                networkSleepDetected = true
                Log.w("AWR555", "Спящий режим сети: Система сообщает об отсутствии интернета.")
                break // Прерываем цикл, проверять сайты дальше нет смысла
            }

            val resultHost = checkHost(resource.url)
            MainRepository.updateOrAddResourceStatus(resource.url, resultHost)
            when (resultHost.firstOrNull()) {
                'Н', 'П', '4', '5' -> failedTries++
                '2', '3' -> successTries++
                else -> failedTries++
            }
                Log.d("AWR555", resultHost)
            }
            when {
                networkSleepDetected -> "Network Sleeping" // Наш новый статус!
                successTries > failedTries -> "Available"
                else -> "Not available"
            }
        }
    }
    suspend fun testWhiteList(context: Context): String{
        val list = ResourcesList.WhiteList.resourcesList.asSequence().shuffled().take(3).toList()
        return testGivenList(context, list)
    }
    suspend fun testRussianWebsites(context: Context):String{
        val list = ResourcesList.RussianWebsites.resourcesList.asSequence().shuffled().take(3).toList()
        return testGivenList(context, list)
    }
    suspend fun testForeignWebsites(context: Context): String{
        val list = ResourcesList.ForeignWebsites.resourcesList.asSequence().shuffled().take(3).toList()
        return testGivenList(context, list)
    }
    suspend fun testBannedWebsites(context: Context): String{
        val list = ResourcesList.BannedWebsites.resourcesList.asSequence().shuffled().take(3).toList()
        return testGivenList(context, list)
    }

    fun checkHost(url: String): String {
        var resultMessage = "None"
        try {
            RetrofitInstance.prepareCall(url)
            val check = RetrofitInstance.call!!.execute()
            resultMessage = check.code.toString()
        } catch (e: Exception) {
            if (e is UnknownHostException) {
                resultMessage = "No connection"
            }
            if (e is SocketTimeoutException) {
                resultMessage = "Timeout"
            }
            Log.e("AWR", e.message.toString())
            Log.e("AWR", e.javaClass.toString())
        }
        return resultMessage
    }
    // Вспомогательная функция для проверки системного статуса интернета
    private fun isSystemNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}