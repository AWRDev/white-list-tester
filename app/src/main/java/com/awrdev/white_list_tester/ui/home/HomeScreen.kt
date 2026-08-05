package com.awrdev.white_list_tester.ui.home

import android.Manifest
import android.R
import android.app.NotificationManager
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startForegroundService
import com.awrdev.white_list_tester.api.ConnectivityTester.testBannedWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testForeignWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testRussianWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testWhiteList
import com.awrdev.white_list_tester.repository.MainRepository
import com.awrdev.white_list_tester.services.NotificationService
import com.awrdev.white_list_tester.ui.home.components.sms_info.AllowSMSCard
import com.awrdev.white_list_tester.ui.home.components.sms_info.AllowSMSDialog
import com.awrdev.white_list_tester.ui.home.components.ListStatusCard
import com.awrdev.white_list_tester.ui.home.components.transport_type_info.TransportTypeCard
import com.awrdev.white_list_tester.ui.home.components.transport_type_info.TransportTypeInfoDialog
import java.time.LocalDateTime

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel, action: (Int)->Unit) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("AWR", "YAHSJHAKSAK")

        } else {
            Log.d("AWR", "ффффффффффффффффффффффффффф")

        }
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val whiteListStatus = remember { mutableStateOf("Not checked yet") }
    val RussiaStatus = remember { mutableStateOf("Not checked yet") }
    val ForeignStatus = remember { mutableStateOf("Not checked yet") }
    val BannedStatus = remember { mutableStateOf("Not checked yet") }

    val isDialogShown = remember { mutableStateOf(false) }
    val isSMSDialogShown = remember { mutableStateOf(false) }

    val currentStatus = remember { mutableStateOf("None") }
    Column(modifier = modifier.padding(8.dp)) {
        AllowSMSCard(modifier = Modifier.fillMaxWidth()
            .clickable(onClick = {
                isSMSDialogShown.value = true
            }))
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
            requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
            scope.launch {
                MainRepository.updateOrSetLasTimeOfCheck(LocalDateTime.now())
                whiteListStatus.value = "In progress"
                RussiaStatus.value = "In progress"
                ForeignStatus.value = "In progress"
                BannedStatus.value = "In progress"

                whiteListStatus.value = testWhiteList()
                RussiaStatus.value = testRussianWebsites()
                ForeignStatus.value = testForeignWebsites()
                BannedStatus.value = testBannedWebsites()
                if (whiteListStatus.value == "Available"){
                    MainRepository.updateOrSetLevel(1)
                }
                if (RussiaStatus.value == "Available"){
                    MainRepository.updateOrSetLevel(2)
                }
                if (ForeignStatus.value == "Available"){
                    MainRepository.updateOrSetLevel(3)
                }
                if (BannedStatus.value == "Available"){
                    MainRepository.updateOrSetLevel(4)
                }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Новая проверка")
        }
        Button(onClick = {
            viewModel.action(context)

            val notification = NotificationCompat.Builder(context, "status")
                .setContentTitle("Статус")
                .setContentText(MainRepository.getCurrentStatus())
                .setSmallIcon(R.drawable.btn_plus)
                .setOngoing(true)
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1, notification.build())
        }) {
            Text(text = "Уведомление")
        }
    }
    if (isDialogShown.value) {
        TransportTypeInfoDialog(
            onDismissRequest = { isDialogShown.value = false },
            transportType = getNetworkType(context)
        )
    }
    if (isSMSDialogShown.value) {
        AllowSMSDialog(
            onDismissRequest = { isSMSDialogShown.value = false },
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