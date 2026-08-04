package com.awrdev.white_list_tester.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun TransportTypeInfoDialog(modifier: Modifier = Modifier, onDismissRequest: ()-> Unit, transportType: String) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TransportTypeCard(transportType = transportType)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Во время проверки убедитесь, что выход в Интернет осуществляется через мобильную сеть, так как на проводном соединении белые списки не применяются (по крайней мере, пока)")
            Button(onClick = {onDismissRequest()}) {
                Text(text = "Хорошо, я поменяю")
            }
            Text(text = "Я знаю, что делаю", modifier = Modifier.clickable(onClick = {onDismissRequest()}))
        }
    }

}