package com.awrdev.white_list_tester.ui.home.components.info_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun StatusInfoBar(modifier: Modifier = Modifier, expand: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatusCard(modifier = Modifier.fillMaxWidth().weight(1f), color = Color.Green) {
            Icon(imageVector = Icons.Default.Phone, contentDescription = "ok")
            Icon(imageVector = Icons.Default.Check, contentDescription = "ok")
        }
        StatusCard(modifier = Modifier.fillMaxWidth().weight(1f), color = Color.Green) {
            Icon(imageVector = Icons.Default.MailOutline, contentDescription = "ok")
            Icon(imageVector = Icons.Default.Check, contentDescription = "ok")
        }
        StatusCard(modifier = Modifier.fillMaxWidth().weight(1f), color = Color.Yellow) {
            Icon(imageVector = Icons.Default.Info, contentDescription = "ok")
            Icon(imageVector = Icons.Default.Warning, contentDescription = "ok")
        }
//        Card(modifier = Modifier
//            .fillMaxWidth()
//            .weight(1f)
//            .background(Color.Yellow, shape = RoundedCornerShape(12.dp))
//            .padding(4.dp)) {
//            Row(modifier = Modifier
//                .background(Color.Yellow)
//                .fillMaxWidth()) {
//                Icon(imageVector = Icons.Default.Phone, contentDescription = "ok")
//                Icon(imageVector = Icons.Default.Check, contentDescription = "ok")
//            }
//        }
        Card(modifier = Modifier.fillMaxWidth().weight(1f).height(30.dp).clickable(onClick = {expand()}),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "ok")        }
            }
    }
}

@Composable
fun StatusCard(modifier: Modifier = Modifier, color: Color,content: @Composable RowScope.() -> Unit) {
    Card(modifier = modifier
        .background(color, shape = RoundedCornerShape(12.dp))
        .padding(4.dp)
    ) {
        Row(modifier = Modifier.background(color).fillMaxWidth()) {
            content()
        }
    }
}