package com.awrdev.white_list_tester.ui.home

import android.Manifest
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.awrdev.white_list_tester.api.RetrofitInstance
import com.awrdev.white_list_tester.ui.home.components.HostStatusCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.typeOf
import kotlin.time.Duration.Companion.milliseconds
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.Row
import com.awrdev.white_list_tester.ui.home.components.TransportTypeCard

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val message = remember { mutableStateOf("Неизвестно") }
    val messageOzon = remember { mutableStateOf("Неизвестно") }
    val messagePikabu = remember { mutableStateOf("Неизвестно") }
    val messageYoutube = remember { mutableStateOf("Неизвестно") }
    Column(modifier = modifier.padding(8.dp)) {
        TransportTypeCard(modifier = Modifier.fillMaxWidth(), transportType = getNetworkType(context))
        Spacer(modifier = Modifier.height(10.dp))
        HostStatusCard(modifier = Modifier.fillMaxWidth().height(100.dp),statusCode = message.value)
        Spacer(modifier = Modifier.height(10.dp))
        HostStatusCard(modifier = Modifier.fillMaxWidth().height(100.dp),statusCode = messageOzon.value)
        Spacer(modifier = Modifier.height(10.dp))
        HostStatusCard(modifier = Modifier.fillMaxWidth().height(100.dp),statusCode = messagePikabu.value)
        Spacer(modifier = Modifier.height(10.dp))
        HostStatusCard(modifier = Modifier.fillMaxWidth().height(100.dp),statusCode = messageYoutube.value)

        Button(onClick = {
            scope.launch {
                message.value = "Проверяем..."
                messageOzon.value = "Проверяем..."
                messagePikabu.value = "Проверяем..."
                messageYoutube.value = "Проверяем..."
                delay(1000L.milliseconds)

                withContext(Dispatchers.IO) {
                    message.value = checkHost("https://yandex.ru") + " Яндекс"
                }
                withContext(Dispatchers.IO) {
                    messageOzon.value =  checkHost("https://ozon.ru") + " Озон"

                }
                withContext(Dispatchers.IO) {
                    messagePikabu.value =  checkHost("https://pikabu.ru") + " Пикабу"

                }
                withContext(Dispatchers.IO) {
                    messageYoutube.value =  checkHost("https://youtube.com") + " Ютаб"

                }
            }

        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Начать проверку")
        }
    }
}

fun checkHost(url: String): String{
    var resultMessage = "None"
    try{
        RetrofitInstance.prepareCall(url)
        val check = RetrofitInstance.call!!.execute()
        resultMessage = check.code.toString()
    } catch (e: Exception){
        if (e is UnknownHostException){
            resultMessage = "No connection"
        }
        if (e is SocketTimeoutException){
            resultMessage = "Timeout"
        }
        Log.e("AWR", e.message.toString())
        Log.e("AWR", e.javaClass.toString())
    }
    return resultMessage
}



@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun getNetworkType(context: Context): String {
    // Получаем ConnectivityManager из контекста системы
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Получаем текущую активную сеть
    val activeNetwork = connectivityManager.activeNetwork ?: return "Нет подключения"

    // Получаем характеристики (возможности) активной сети
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return "Нет подключения"

    return when {
        // Проверяем подключение через Wi-Fi
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi"

        // Проверяем подключение через мобильные данные (3G/4G/5G)
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Мобильная связь"

        // Проверяем проводное подключение (Ethernet)
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"

        else -> "Другой тип подключения"
    }
}
