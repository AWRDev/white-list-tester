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
import com.awrdev.white_list_tester.ui.theme.WhitelisttesterTheme

@Composable
fun HostStatusCard(modifier: Modifier = Modifier, resourceName:String, statusCode: String) {
    var cardColor: Color? = null
    cardColor = when(statusCode[0]){
        'Н' -> Color.hsv(34f, 0.13f, 0.76f)
        'П' -> Color.hsv(59f, 0.29f, 0.92f)
        '2' -> Color.hsv(142f, 0.96f, 0.88f)
        '3' -> Color.hsv(50f, 0.96f, 0.88f)
        '4' -> Color.hsv(11f, 0.96f, 0.88f)
        '5' -> Color.hsv(11f, 0.96f, 0.88f)
        else -> Color.hsv(11f, 0.96f, 0.88f)
    }
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = cardColor)) {
        Column(modifier = Modifier.fillMaxWidth().height(100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$resourceName $statusCode", color = Color.Black, fontSize = 24.sp)
        }
    }
}