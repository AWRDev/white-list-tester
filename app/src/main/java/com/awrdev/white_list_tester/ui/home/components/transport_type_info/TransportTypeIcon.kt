package com.awrdev.white_list_tester.ui.home.components.transport_type_info

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awrdev.white_list_tester.ConnectionTypes

@Composable
fun TransportTypeIcon(modifier: Modifier = Modifier, transportType: ConnectionTypes) {
    when (transportType) {
        ConnectionTypes.WI_FI -> return Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning")
        ConnectionTypes.CELLULAR -> return Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Good"
        )

        ConnectionTypes.ETHERNET -> return Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Warning"
        )

        ConnectionTypes.USES_VPN -> return Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Wrong"
        )


        else -> return Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning")
    }


}