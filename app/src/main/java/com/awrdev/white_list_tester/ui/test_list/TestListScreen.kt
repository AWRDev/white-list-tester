package com.awrdev.white_list_tester.ui.test_list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.awrdev.white_list_tester.ResourcesList
import com.awrdev.white_list_tester.api.RetrofitInstance
import com.awrdev.white_list_tester.repository.MainRepository
import com.awrdev.white_list_tester.ui.home.components.HostStatusCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun TestListScreen(modifier: Modifier = Modifier, listToCheck: Int, back: ()->Unit) {
    val scope = rememberCoroutineScope()
    val message = remember { mutableStateOf("Не проверено") }
    val status = remember { mutableStateListOf<String>() }
    val listToCheck = when(listToCheck){
        1 -> ResourcesList.WhiteList
        2 -> ResourcesList.RussianWebsites
        3 -> ResourcesList.ForeignWebsites
        4 -> ResourcesList.BannedWebsites
        else -> ResourcesList.BannedWebsites
    }
    for (item in listToCheck.resourcesList){
        if (item.url in MainRepository.savedStatuses){
            status.add("${MainRepository.savedStatuses[item.url]}")
        }
        else{
            status.add("Не проверено")
        }
    }
    LazyColumn(modifier = modifier) {
        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {back()}) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                    Text(text = listToCheck.listName,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 24.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        scope.launch {
                            for (i in status.indices){
                                status[i] = "Проверяем"
                                Log.d("AWR1", i.toString())
                            }
                            delay(1000L.milliseconds)

                            for ((i, element) in listToCheck.resourcesList.withIndex()){
                                withContext(Dispatchers.IO) {
                                    MainRepository.updateOrAddResourceStatus(element.url, checkHost(element.url))
                                    status[i] = checkHost(element.url)
                                    Log.d("AWR2", i.toString())
                                }
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Refresh,
                            contentDescription = "Проверить",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Options",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }

            }
            Spacer(modifier = Modifier.height(15.dp))
        }
        itemsIndexed(listToCheck.resourcesList){key, item ->
            HostStatusCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            scope.launch {
                                status[key] = "Проверяем"
                                withContext(Dispatchers.IO) {
                                    MainRepository.updateOrAddResourceStatus(item.url, checkHost(item.url))
                                    status[key] = checkHost(item.url)
                                }

                            }
                        }
                    ),
                resourceName = item.name,
                statusCode = status[key]
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

fun checkHost(url: String): String {
    var resultMessage = "None"
    try {
        RetrofitInstance.prepareCall(url)
        val check = RetrofitInstance.call!!.execute()
        resultMessage = check.code.toString()
    } catch (e: Exception) {
        if (e is UnknownHostException) {
            resultMessage = "No connection"
        }
        if (e is SocketTimeoutException) {
            resultMessage = "Timeout"
        }
        Log.e("AWR", e.message.toString())
        Log.e("AWR", e.javaClass.toString())
    }
    return resultMessage
}