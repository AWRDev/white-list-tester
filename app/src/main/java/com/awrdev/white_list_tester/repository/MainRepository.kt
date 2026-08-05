package com.awrdev.white_list_tester.repository

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
            0 -> return "Not defined"
            1 -> return "Белый список"
            2 -> return "Россия"
            3 -> return "Норм"
            4 -> return "Оттепель!"
            else -> return "N/A"
        }
    }

    fun updateOrSetLasTimeOfCheck(newTimeOfCheck: LocalDateTime){
        lastTimeOfCheck.value = newTimeOfCheck
    }
}