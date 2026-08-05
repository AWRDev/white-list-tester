package com.awrdev.white_list_tester

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "status",
            "Service status",
            NotificationManager.IMPORTANCE_LOW
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}