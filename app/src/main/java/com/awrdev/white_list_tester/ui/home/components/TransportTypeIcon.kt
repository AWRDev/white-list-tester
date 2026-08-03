package com.awrdev.white_list_tester.ui.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TransportTypeIcon(modifier: Modifier = Modifier, transportType: String) {
    when (transportType) {
        "Wi-Fi" -> return Icon(imageVector = Icons.Default.Warning, contentDescription = "Warnig")
        "Мобильная связь" -> return Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Good"
        )

        "Ethernet" -> return Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Warnig"
        )

        else -> return Icon(imageVector = Icons.Default.Warning, contentDescription = "Warnig")
    }


}