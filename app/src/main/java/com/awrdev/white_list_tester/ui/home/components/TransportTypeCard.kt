package com.awrdev.white_list_tester.ui.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TransportTypeCard(modifier: Modifier = Modifier, transportType: String) {
    val containerColor = when(transportType){
        "Wi-Fi" -> Color.hsv(50f, 0.96f, 0.88f)
        "Мобильная связь" -> Color.hsv(142f, 0.96f, 0.88f)
        "Ethernet" -> Color.hsv(50f, 0.96f, 0.88f)
        else -> Color.hsv(50f, 0.96f, 0.88f)
    }
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = containerColor)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.padding(8.dp), text = transportType)
            TransportTypeIcon(transportType = transportType)
        }
    }

}