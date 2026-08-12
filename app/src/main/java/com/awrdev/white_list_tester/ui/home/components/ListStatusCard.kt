package com.awrdev.white_list_tester.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awrdev.white_list_tester.repository.MainRepository
import com.awrdev.white_list_tester.repository.MainRepository.getReadableTime
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun ListStatusCard(modifier: Modifier = Modifier, title: String, status: String) {
    Card(modifier = modifier.padding(vertical = 2.dp)) {
        Row(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = title)
            Row() {
                if (status != "Not checked yet"){
                    Text(text = "${getReadableTime(MainRepository.lastTimeOfCheck.value)} • ")
                }
                when (status){
                    "Not checked yet" -> Icon(imageVector = Icons.Default.DateRange, contentDescription = status)
                    "In progress" -> Icon(imageVector = Icons.Default.Refresh, contentDescription = status)
                    "Available" -> Icon(imageVector = Icons.Default.Check, contentDescription = status)
                    "Partially available" -> Icon(imageVector = Icons.Default.Warning, contentDescription = status)
                    "Not available" -> Icon(imageVector = Icons.Default.Close, contentDescription = status)
                }
            }
        }
    }
}

