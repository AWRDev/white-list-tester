package com.awrdev.white_list_tester.ui.home.components.transport_type_info

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.awrdev.white_list_tester.ConnectionTypes
import com.awrdev.white_list_tester.ui.home.components.transport_type_info.TransportType.Companion.getContainerColor
import com.awrdev.white_list_tester.ui.home.components.transport_type_info.TransportType.Companion.getTypeTitle

@Composable
fun TransportTypeCard(modifier: Modifier = Modifier, transportType: ConnectionTypes) {
    val containerColor = getContainerColor(transportType)
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = containerColor)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.padding(8.dp), text = getTypeTitle(transportType))
            TransportTypeIcon(transportType = transportType)
        }
    }

}