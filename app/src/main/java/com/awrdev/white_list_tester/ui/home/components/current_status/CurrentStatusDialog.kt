package com.awrdev.white_list_tester.ui.home.components.current_status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.awrdev.white_list_tester.repository.MainRepository

@Composable
fun CurrentStatusDialog(modifier: Modifier = Modifier, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(16.dp))
            .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = MainRepository.getCurrentStatus(), modifier = Modifier.fillMaxWidth(), fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Start)
            Text(text = MainRepository.getCurrentStatusDetailed(), modifier = Modifier.padding(8.dp))
            Button(onClick = {onDismissRequest()}) {
                Text(text = "Понятно")
            }
        }
    }

}