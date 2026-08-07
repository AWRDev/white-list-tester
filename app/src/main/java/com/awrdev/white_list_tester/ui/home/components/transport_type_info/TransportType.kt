package com.awrdev.white_list_tester.ui.home.components.transport_type_info

import androidx.compose.ui.graphics.Color
import com.awrdev.white_list_tester.ui.theme.GreenBasic
import com.awrdev.white_list_tester.ui.theme.YellowBasic

class TransportType {
    companion object {
        fun getContainerColor(transportType: String) = when(transportType){
            "Wi-Fi" -> YellowBasic
            "Мобильная связь" -> GreenBasic
            "Ethernet" -> YellowBasic
            else -> YellowBasic
        }
    }
}