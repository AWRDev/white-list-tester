package com.awrdev.white_list_tester.ui.home.components.sms_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AllowSMSDialog(modifier: Modifier = Modifier, onDismissRequest: ()-> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(16.dp))
            .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Разрешите доступ к СМС, чтобы приложение могло узнавать о предупреждениях МЧС (это необязательно)")
            Button(onClick = {onDismissRequest()}) {
                Text(text = "Понятно")
            }
        }
    }

}