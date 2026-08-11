package com.awrdev.white_list_tester.ui.home.components.sms_info

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.awrdev.white_list_tester.repository.MainRepository
import com.awrdev.white_list_tester.ui.theme.GreenBasic
import com.awrdev.white_list_tester.ui.theme.YellowBasic

@Composable
fun AllowSMSCard(modifier: Modifier = Modifier, isAllowed: Boolean) {
    val containerColor = when(isAllowed){
        true -> GreenBasic
        false -> YellowBasic
    }
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = containerColor)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.padding(8.dp), text = "Разрешите СМС")
            when(isAllowed){
                true -> Icon(imageVector = Icons.Default.MailOutline, contentDescription = "SMS is allowed")
                false -> Icon(imageVector = Icons.Default.MailOutline, contentDescription = "SMS isn't allowed")
            }

        }
    }

}