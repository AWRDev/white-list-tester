package com.awrdev.white_list_tester.ui.home.components.air_alert

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awrdev.white_list_tester.ui.home.components.transport_type_info.TransportTypeIcon
import com.awrdev.white_list_tester.ui.theme.YellowBasic

@Composable
fun AirAlertCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = YellowBasic)) {
        Text(modifier = Modifier.padding(8.dp), text = "Угроза БПЛА")
    }
}