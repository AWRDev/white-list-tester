package com.awrdev.white_list_tester.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.awrdev.white_list_tester.api.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val message = remember { mutableStateOf("Неизвестно") }
    Column(modifier = modifier.padding(8.dp)) {
        Card(modifier = Modifier.fillMaxWidth().height(100.dp)) {
            Column(modifier = Modifier.fillMaxWidth().height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = message.value)
            }
        }
        Button(onClick = {
            runBlocking{
                val check = RetrofitInstance.api.getYandex()
                message.value = check.message()
            }

        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Проверить")
        }
    }
}