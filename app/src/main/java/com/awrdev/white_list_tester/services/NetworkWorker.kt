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
            Log.d("WORKER", "Worker has been started")
            MainRepository.updateListStatus("WL", testWhiteList(localContext))
            MainRepository.updateListStatus("RU", testRussianWebsites(localContext))
            MainRepository.updateListStatus("WWW", testForeignWebsites(localContext))
            MainRepository.updateListStatus("BAN", testBannedWebsites(localContext))

            if (MainRepository.whiteListStatus.value == "Network Sleeping"){
                return Result.retry()
            }
            MainRepository.updateOrSetLevel(0)
            if (MainRepository.whiteListStatus.value == "Available"){
                MainRepository.updateOrSetLevel(1)
            }
            if (MainRepository.RussiaStatus.value == "Available"){
                MainRepository.updateOrSetLevel(2)
            }
            if (MainRepository.ForeignStatus.value == "Available"){
                MainRepository.updateOrSetLevel(3)
            }
            if (MainRepository.BannedStatus.value == "Available"){
                MainRepository.updateOrSetLevel(4)
            }
            val notification = NotificationCompat.Builder(localContext, "status")
                .setContentTitle("Статус")
                .setContentText(MainRepository.getCurrentStatus())
                .setSmallIcon(MainRepository.getCurrentStatusIcon())
                .setOngoing(true)
            val manager = localContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1, notification.build())
            Result.success() // Всё прошло успешно
        } catch (e: Exception) {
            Result.retry()   // Если интернет отвалился, система попробует позже
        }
    }
}