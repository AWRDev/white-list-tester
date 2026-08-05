package com.awrdev.white_list_tester.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.awrdev.white_list_tester.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class NotificationService: Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        Log.d("AWR", "Cerated")
        super.onCreate()
        }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AWR123", "onStart")
        start()
        return super.onStartCommand(intent, flags, startId)
    }


    private fun start() {

    }
}