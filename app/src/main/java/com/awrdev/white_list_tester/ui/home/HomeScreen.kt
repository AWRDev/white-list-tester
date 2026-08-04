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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import com.awrdev.white_list_tester.api.ConnectivityTester.testBannedWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testForeignWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testRussianWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testWhiteList
import com.awrdev.white_list_tester.ui.home.components.ListStatusCard
import com.awrdev.white_list_tester.ui.home.components.TransportTypeCard
import com.awrdev.white_list_tester.ui.home.components.TransportTypeInfoDialog

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, action: (Int)->Unit) {
    val scope = rememberCoroutineScope()
    val scope1 = rememberCoroutineScope()
    val scope2 = rememberCoroutineScope()
    val scope3 = rememberCoroutineScope()
    val context = LocalContext.current

    val whiteListStatus = remember { mutableStateOf("Not checked yet") }
    val RussiaStatus = remember { mutableStateOf("Not checked yet") }
    val ForeignStatus = remember { mutableStateOf("Not checked yet") }
    val BannedStatus = remember { mutableStateOf("Not checked yet") }

    val isDialogShown = remember { mutableStateOf(false) }
    Column(modifier = modifier.padding(8.dp)) {
        TransportTypeCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    isDialogShown.value = true
                }), transportType = getNetworkType(context)
        )
        Spacer(modifier = Modifier.height(10.dp))

        ListStatusCard(modifier = Modifier.fillMaxWidth().height(50.dp).clickable(onClick = {
            action(1)
        }), title = "Белый список", status = whiteListStatus.value)
        ListStatusCard(modifier = Modifier.fillMaxWidth().height(50.dp).clickable(onClick = {
            action(2)
        }), title = "Российские сайты", status = RussiaStatus.value)
        ListStatusCard(modifier = Modifier.fillMaxWidth().height(50.dp).clickable(onClick = {
            action(3)
        }), title = "Зарубежные сайты", status = ForeignStatus.value)
        ListStatusCard(modifier = Modifier.fillMaxWidth().height(50.dp).clickable(onClick = {
            action(4)
        }), title = "Заблокированные сайты", status = BannedStatus.value)
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
            scope.launch {
                whiteListStatus.value = testWhiteList()
                RussiaStatus.value = testRussianWebsites()
                ForeignStatus.value = testForeignWebsites()
                BannedStatus.value = testBannedWebsites()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Новая проверка")
        }
    }
    if (isDialogShown.value) {
        TransportTypeInfoDialog(
            onDismissRequest = { isDialogShown.value = false },
            transportType = getNetworkType(context)
        )
    }
}


@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun getNetworkType(context: Context): String {
    // Получаем ConnectivityManager из контекста системы
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Получаем текущую активную сеть
    val activeNetwork = connectivityManager.activeNetwork ?: return "Нет подключения"

    // Получаем характеристики (возможности) активной сети
    val capabilities =
        connectivityManager.getNetworkCapabilities(activeNetwork) ?: return "Нет подключения"

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

@Composable
fun GetFakeStatus(): Unit{
    return arrayOf(
        Icon(imageVector = Icons.Default.Check, contentDescription = ""),
        Icon(imageVector = Icons.Default.Refresh, contentDescription = ""),
        Icon(imageVector = Icons.Default.Close, contentDescription = "")).random()
}