package com.awrdev.white_list_tester.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.awrdev.white_list_tester.services.NetworkWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration

//@HiltViewModel
class HomeViewModel : ViewModel() {
    fun periodicRequest(context: Context){
        val networkWorkRequest = PeriodicWorkRequestBuilder<NetworkWorker>(repeatInterval = 100.toDuration(DurationUnit.SECONDS).toJavaDuration()).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork("periodic",
            ExistingPeriodicWorkPolicy.REPLACE, networkWorkRequest)
    }

    fun singleRequest(context: Context){
        val networkWorkRequest = OneTimeWorkRequestBuilder<NetworkWorker>().build()
        WorkManager.getInstance(context).enqueue(networkWorkRequest)
    }
}