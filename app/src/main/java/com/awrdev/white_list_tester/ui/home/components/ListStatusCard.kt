package com.awrdev.white_list_tester.ui.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListStatusCard(modifier: Modifier = Modifier, title: String) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = title)
            Icon(imageVector = Icons.Default.Check, contentDescription = "Available")
        }
    }
}