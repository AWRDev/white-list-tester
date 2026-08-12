package com.awrdev.white_list_tester.ui.home.components.transport_type_info

import androidx.compose.ui.graphics.Color
import com.awrdev.white_list_tester.ConnectionTypes
import com.awrdev.white_list_tester.ui.theme.GreenBasic
import com.awrdev.white_list_tester.ui.theme.RedBasic
import com.awrdev.white_list_tester.ui.theme.YellowBasic

class TransportType {
    companion object {
        fun getContainerColor(transportType: ConnectionTypes) = when(transportType){
            ConnectionTypes.WI_FI ->  YellowBasic
            ConnectionTypes.CELLULAR -> GreenBasic
            ConnectionTypes.ETHERNET -> YellowBasic
            ConnectionTypes.USES_VPN -> RedBasic
            else -> YellowBasic
        }
        fun getTypeTitle(transportType: ConnectionTypes) = when(transportType){
            ConnectionTypes.WI_FI ->  "Wi-Fi"
            ConnectionTypes.CELLULAR -> "Мобильная связь"
            ConnectionTypes.ETHERNET -> "Ethernet"
            ConnectionTypes.USES_VPN -> "VPN"
            else -> "Другой тип подключения"
        }
    }
}