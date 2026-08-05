package com.awrdev.white_list_tester.services

import android.R
import android.app.NotificationManager
import android.content.Context
import android.content.ContextParams
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.awrdev.white_list_tester.api.ConnectivityTester.testBannedWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testForeignWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testRussianWebsites
import com.awrdev.white_list_tester.api.ConnectivityTester.testWhiteList
import com.awrdev.white_list_tester.repository.MainRepository

class NetworkWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams) {
    val localContext = context
    override suspend fun doWork(): Result {
        return try {
            val whiteListStatus =  mutableStateOf("Not checked yet")
            val RussiaStatus =  mutableStateOf("Not checked yet")
            val ForeignStatus =  mutableStateOf("Not checked yet")
            val BannedStatus =  mutableStateOf("Not checked yet")
            // Выполняем ваш запрос в интернет (например, через Retrofit / Ktor)
            Log.d("WORKER", "OOOOOOOOOOOOOOOOOOOOO")
            whiteListStatus.value = testWhiteList()
            RussiaStatus.value = testRussianWebsites()
            ForeignStatus.value = testForeignWebsites()
            BannedStatus.value = testBannedWebsites()
            if (whiteListStatus.value == "Available"){
                MainRepository.updateOrSetLevel(1)
            }
            if (RussiaStatus.value == "Available"){
                MainRepository.updateOrSetLevel(2)
            }
            if (ForeignStatus.value == "Available"){
                MainRepository.updateOrSetLevel(3)
            }
            if (BannedStatus.value == "Available"){
                MainRepository.updateOrSetLevel(4)
            }
            val notification = NotificationCompat.Builder(localContext, "status")
                .setContentTitle("Статус")
                .setContentText(MainRepository.getCurrentStatus())
                .setSmallIcon(R.drawable.ic_notification_overlay)
                .setOngoing(true)
            val manager = localContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1, notification.build())
            Result.success() // Всё прошло успешно
        } catch (e: Exception) {
            Result.retry()   // Если интернет отвалился, система попробует позже
        }
    }
}