package com.awrdev.white_list_tester.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.awrdev.white_list_tester.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val message = remember { mutableStateOf("Неизвестно") }
    val messageOzon = remember { mutableStateOf("Неизвестно") }
    val messagePikabu = remember { mutableStateOf("Неизвестно") }
    Column(modifier = modifier.padding(8.dp)) {
        Card(modifier = Modifier.fillMaxWidth().height(100.dp)) {
            Column(modifier = Modifier.fillMaxWidth().height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = message.value)
            }
        }

        Card(modifier = Modifier.fillMaxWidth().height(100.dp)) {
            Column(modifier = Modifier.fillMaxWidth().height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = messageOzon.value)
            }
        }

        Card(modifier = Modifier.fillMaxWidth().height(100.dp)) {
            Column(modifier = Modifier.fillMaxWidth().height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = messagePikabu.value)
            }
        }
        Button(onClick = {
            scope.launch {
                message.value = "Проверяем..."
                messageOzon.value = "Проверяем..."
                messagePikabu.value = "Проверяем..."
                delay(1000L.milliseconds)

                withContext(Dispatchers.IO) {
                    RetrofitInstance.prepareCall("https://yandex.ru")
                    val check = RetrofitInstance.call!!.execute()
                    message.value = check.code.toString()
                }
                withContext(Dispatchers.IO) {
                    RetrofitInstance.prepareCall("https://ozon.ru")
                    val check = RetrofitInstance.call!!.execute()
                    messageOzon.value = check.code.toString()
                }
                withContext(Dispatchers.IO) {
                    RetrofitInstance.prepareCall("https://pikabu.ru")
                    val check = RetrofitInstance.call!!.execute()
                    messagePikabu.value = check.code.toString()
                }
            }

        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Начать проверку")
        }
    }
}