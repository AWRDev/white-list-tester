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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startForegroundService
import com.awrdev.white_list_tester.ConnectionTypes
import com.awrdev.white_list_tester.api.ConnectivityTester.testBannedWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testForeignWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testRussianWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testWhiteList
import com.awrdev.white_list_tester.repository.MainRepository
import com.awrdev.white_list_tester.services.NotificationService
import com.awrdev.white_list_tester.ui.home.components.sms_info.AllowSMSCard
import com.awrdev.white_list_tester.ui.home.components.sms_info.AllowSMSDialog
import com.awrdev.white_list_tester.ui.home.components.ListStatusCard
import com.awrdev.white_list_tester.ui.home.components.air_alert.AirAlertDialog
import com.awrdev.white_list_tester.ui.home.components.current_status.CurrentStatusDialog
import com.awrdev.white_list_tester.ui.home.components.info_bar.StatusInfoBar
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
            Log.d("AWR", "SMS are granted")

        } else {
            Log.d("AWR", "SMS are denied")

        }
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    val isDialogShown = remember { mutableStateOf(false) }
    val isSMSDialogShown = remember { mutableStateOf(false) }
    val isAirAlertDialogShown = remember { mutableStateOf(false) }
    val isCurrentStatusDialogShown = remember { mutableStateOf(false) }

    val isInfoBarExpanded = remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(8.dp)) {
        StatusInfoBar(modifier = Modifier.fillMaxWidth(), transportType = getNetworkType(context),
            showSMSDialog = {isSMSDialogShown.value = true},
            showTransportTypeDialog = {isDialogShown.value = true},
            showAirAlertDialog = {isAirAlertDialogShown.value = true},
            showCurrentStatusDialog = {isCurrentStatusDialogShown.value = true})
        Spacer(modifier = Modifier.height(10.dp))

        ListStatusCard(modifier = Modifier.fillMaxWidth().height(50.dp).clickable(onClick = {
            action(1)
        }), title = "Белый список", status = MainRepository.whiteListStatus.value)
        ListStatusCard(modifier = Modifier.fillMaxWidth().height(50.dp).clickable(onClick = {
            action(2)
        }), title = "Российские сайты", status = MainRepository.RussiaStatus.value)
        ListStatusCard(modifier = Modifier.fillMaxWidth().height(50.dp).clickable(onClick = {
            action(3)
        }), title = "Зарубежные сайты", status = MainRepository.ForeignStatus.value)
        ListStatusCard(modifier = Modifier.fillMaxWidth().height(50.dp).clickable(onClick = {
            action(4)
        }), title = "Заблокированные сайты", status = MainRepository.BannedStatus.value)
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
            MainRepository.updateOrSetLasTimeOfCheck(LocalDateTime.now())
            MainRepository.whiteListStatus.value = "In progress"
            MainRepository.RussiaStatus.value = "In progress"
            MainRepository.ForeignStatus.value = "In progress"
            MainRepository.BannedStatus.value = "In progress"

            requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
            viewModel.singleRequest(context)

            val notification = NotificationCompat.Builder(context, "status")
                .setContentTitle("Статус")
                .setContentText(MainRepository.getCurrentStatus())
                .setSmallIcon(MainRepository.getCurrentStatusIcon())
                .setOngoing(true)
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1, notification.build())
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Проверить сейчас")
        }

        Button(onClick = {
            viewModel.periodicRequest(context)

            val notification = NotificationCompat.Builder(context, "status")
                .setContentTitle("Статус")
                .setContentText(MainRepository.getCurrentStatus())
                .setSmallIcon(MainRepository.getCurrentStatusIcon())
                .setOngoing(true)
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1, notification.build())
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Периодический воркер")
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
    if (isAirAlertDialogShown.value){
        AirAlertDialog(
            onDismissRequest = {isAirAlertDialogShown.value = false})
    }
    if (isCurrentStatusDialogShown.value){
        CurrentStatusDialog(onDismissRequest = {isCurrentStatusDialogShown.value = false})
    }
}


@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun getNetworkType(context: Context): ConnectionTypes {
    // Получаем ConnectivityManager из контекста системы
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Получаем текущую активную сеть
    val activeNetwork = connectivityManager.activeNetwork ?: return ConnectionTypes.NO_CONNECTION

    // Получаем характеристики (возможности) активной сети
    val capabilities =
        connectivityManager.getNetworkCapabilities(activeNetwork) ?: return ConnectionTypes.NO_CONNECTION

    return when {
        // Проверяем подключение через VPN, первым, чтобы предупредить сразу
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> ConnectionTypes.USES_VPN
        // Проверяем подключение через Wi-Fi
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectionTypes.WI_FI

        // Проверяем подключение через мобильные данные (3G/4G/5G)
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectionTypes.CELLULAR

        // Проверяем проводное подключение (Ethernet)
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectionTypes.ETHERNET


        else -> ConnectionTypes.OTHER
    }
}

@Composable
fun GetFakeStatus(): Unit{
    return arrayOf(
        Icon(imageVector = Icons.Default.Check, contentDescription = ""),
        Icon(imageVector = Icons.Default.Refresh, contentDescription = ""),
        Icon(imageVector = Icons.Default.Close, contentDescription = "")).random()
}