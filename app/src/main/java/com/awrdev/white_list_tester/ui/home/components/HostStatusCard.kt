package com.awrdev.white_list_tester.ui.home.components

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.awrdev.white_list_tester.ui.theme.GreenBasic
import com.awrdev.white_list_tester.ui.theme.GreyBasic
import com.awrdev.white_list_tester.ui.theme.RedBasic
import com.awrdev.white_list_tester.ui.theme.WhitelisttesterTheme
import com.awrdev.white_list_tester.ui.theme.YellowBasic
import com.awrdev.white_list_tester.ui.theme.YellowLight

@Composable
fun HostStatusCard(modifier: Modifier = Modifier, resourceName:String, statusCode: String) {
    val cardColor = when(statusCode[0]){
        'Н' -> GreyBasic
        'П' -> YellowLight
        '2' -> GreenBasic
        '3' -> YellowBasic
        '4' -> RedBasic
        '5' -> RedBasic
        else -> RedBasic
    }
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = cardColor)) {
        Column(modifier = Modifier.fillMaxWidth().height(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$resourceName $statusCode", color = Color.Black, fontSize = 24.sp)
        }
    }
}