package com.awrdev.white_list_tester.api

import android.util.Log
import com.awrdev.white_list_tester.ResourcesList
import com.awrdev.white_list_tester.WebResource
import com.awrdev.white_list_tester.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ConnectivityTester {
    suspend fun testGivenList(givenList: List<WebResource>): String{
        return withContext(Dispatchers.IO){
        var successTries = 0
        var failedTries = 0
            for (resource in givenList){
                val resultHost = checkHost(resource.url)
                MainRepository.updateOrAddResourceStatus(resource.url, resultHost)
                when (resultHost.firstOrNull()) {
                    'Н', 'П', '4', '5' -> failedTries++
                    '2', '3' -> successTries++
                    else -> failedTries++
                }
                    Log.d("AWR555", resultHost)
                }
        if (successTries > failedTries){
            "Available"
        }
        else "Not available"
        }
    }
    suspend fun testWhiteList(): String{
        val list = ResourcesList.WhiteList.resourcesList.asSequence().shuffled().take(3).toList()
        return testGivenList(list)
    }
    suspend fun testRussianWebsites():String{
        val list = ResourcesList.RussianWebsites.resourcesList.asSequence().shuffled().take(3).toList()
        return testGivenList(list)
    }
    suspend fun testForeignWebsites(): String{
        val list = ResourcesList.ForeignWebsites.resourcesList.asSequence().shuffled().take(3).toList()
        return testGivenList(list)
    }
    suspend fun testBannedWebsites(): String{
        val list = ResourcesList.BannedWebsites.resourcesList.asSequence().shuffled().take(3).toList()
        return testGivenList(list)
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

}