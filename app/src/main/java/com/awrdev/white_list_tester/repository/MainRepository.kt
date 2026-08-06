package com.awrdev.white_list_tester.repository

import android.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import java.time.LocalDateTime

object MainRepository {
    val savedStatuses = mutableStateMapOf<String, String>()
    val currentLevel = mutableIntStateOf(0)
    val lastTimeOfCheck = mutableStateOf(LocalDateTime.now())

    fun updateOrAddResourceStatus(resourceUrl: String, newStatus: String){
        savedStatuses[resourceUrl] = newStatus
    }
    fun updateOrSetLevel(level: Int){
        currentLevel.intValue = level
    }
    fun getCurrentStatus(): String{
        when(currentLevel.intValue){
            0 -> return "Нет связи"
            1 -> return "Белый список"
            2 -> return "Россия"
            3 -> return "Норм"
            4 -> return "Оттепель!"
            else -> return "N/A"
        }
    }

    fun getCurrentStatusIcon(): Int{
        when(currentLevel.intValue){
            0 -> return R.drawable.ic_delete
            1 -> return R.drawable.ic_notification_overlay
            2 -> return R.drawable.ic_dialog_alert
            3 -> return R.drawable.presence_online
            4 -> return R.drawable.btn_star_big_on
            else -> return android.R.drawable.ic_menu_help
        }
    }

    fun updateOrSetLasTimeOfCheck(newTimeOfCheck: LocalDateTime){
        lastTimeOfCheck.value = newTimeOfCheck
    }
}