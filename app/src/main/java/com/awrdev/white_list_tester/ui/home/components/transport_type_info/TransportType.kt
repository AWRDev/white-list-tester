package com.awrdev.white_list_tester.ui.home.components.transport_type_info

import androidx.compose.ui.graphics.Color

class TransportType {
    companion object {
        fun getContainerColor(transportType: String) = when(transportType){
            "Wi-Fi" -> Color.hsv(50f, 0.96f, 0.88f)
            "Мобильная связь" -> Color.hsv(142f, 0.96f, 0.88f)
            "Ethernet" -> Color.hsv(50f, 0.96f, 0.88f)
            else -> Color.hsv(50f, 0.96f, 0.88f)
        }
    }
}