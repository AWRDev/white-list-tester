package com.awrdev.white_list_tester.ui.home.components.info_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.awrdev.white_list_tester.ConnectionTypes
import com.awrdev.white_list_tester.repository.MainRepository
import com.awrdev.white_list_tester.ui.home.components.air_alert.AirAlertCard
import com.awrdev.white_list_tester.ui.home.components.sms_info.AllowSMSCard
import com.awrdev.white_list_tester.ui.home.components.transport_type_info.TransportType.Companion.getContainerColor
import com.awrdev.white_list_tester.ui.home.components.transport_type_info.TransportTypeCard
import com.awrdev.white_list_tester.ui.home.components.transport_type_info.TransportTypeIcon
import com.awrdev.white_list_tester.ui.home.getNetworkType
import com.awrdev.white_list_tester.ui.theme.GreenBasic
import com.awrdev.white_list_tester.ui.theme.YellowBasic


@Composable
fun StatusInfoBar(
    modifier: Modifier = Modifier,
    transportType: ConnectionTypes,
    showSMSDialog: () -> Unit,
    showTransportTypeDialog: ()-> Unit,
    showAirAlertDialog: () -> Unit,
    showCurrentStatusDialog: () -> Unit) {
    val context = LocalContext.current

    var isExpanded by remember { mutableStateOf(false) }
    if (isExpanded){
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
            Column(modifier = Modifier.fillMaxWidth().weight(3f)) {
                TransportTypeCard(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .clickable(onClick = {
                            showTransportTypeDialog()}
                        ), transportType = getNetworkType(context)
                )
                AllowSMSCard(modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .clickable(onClick = {showSMSDialog()}),
                    isAllowed = MainRepository.isSMSAllowed.value
                )
                AirAlertCard(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .clickable(onClick = {
                            showAirAlertDialog()
                        }),
                    isActive = MainRepository.isAirAlertActive.value
                )
            }
            Card(modifier = Modifier.fillMaxWidth().weight(1f).fillMaxHeight().clickable(onClick = {isExpanded = false}),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
                Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "ok")
                }
            }
        }
    }
    else{
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatusCard(modifier = Modifier.fillMaxWidth().weight(1f).clickable(onClick = {showTransportTypeDialog()}), color = getContainerColor(transportType)) {
                Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone Icon")
                TransportTypeIcon(transportType = getNetworkType(context))
            }
            StatusCard(modifier = Modifier.fillMaxWidth().weight(1f).clickable(onClick = {showSMSDialog()}), color = MainRepository.getSMSStatusColor()) {
                Icon(imageVector = Icons.Default.MailOutline, contentDescription = "SMS Icon")
                when(MainRepository.isSMSAllowed.value){
                    true -> Icon(imageVector = Icons.Default.Check, contentDescription = "OK")
                    false -> Icon(imageVector = Icons.Default.Close, contentDescription = "Не разрешено")
                }
            }
            StatusCard(modifier = Modifier.fillMaxWidth().weight(1f).clickable(onClick = {showAirAlertDialog()}), color = MainRepository.getAirAlertColor()) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Air Alert Icon")
                when(MainRepository.isAirAlertActive.value){
                    true -> Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning")
                    false -> Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "Спокойно")
                }
            }
    //        Card(modifier = Modifier
    //            .fillMaxWidth()
    //            .weight(1f)
    //            .background(Color.Yellow, shape = RoundedCornerShape(12.dp))
    //            .padding(4.dp)) {
    //            Row(modifier = Modifier
    //                .background(Color.Yellow)
    //                .fillMaxWidth()) {
    //                Icon(imageVector = Icons.Default.Phone, contentDescription = "ok")
    //                Icon(imageVector = Icons.Default.Check, contentDescription = "ok")
    //            }
    //        }
            Card(modifier = Modifier.padding(4.dp).fillMaxWidth().weight(1f).fillMaxHeight().clickable(onClick = {isExpanded = true}),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
                Row(modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "ok")        }
                }
        }

    }
    Card(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth().clickable(onClick = {showCurrentStatusDialog()}),
        colors = CardDefaults.cardColors(containerColor = MainRepository.getCurrentStatusColor())) {
        Text(modifier = Modifier.padding(8.dp), text = "Текущий статус: ${MainRepository.getCurrentStatus()} • ${MainRepository.getReadableTime(
            MainRepository.lastTimeOfCheck.value)}")
    }

}

@Composable
fun StatusCard(modifier: Modifier = Modifier, color: Color,content: @Composable RowScope.() -> Unit) {
    Card(modifier = modifier
        .padding(4.dp)
        .background(color, shape = RoundedCornerShape(12.dp))
        .padding(4.dp)
    ) {
        Row(modifier = Modifier.background(color).fillMaxWidth().padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            content()
        }
    }
}