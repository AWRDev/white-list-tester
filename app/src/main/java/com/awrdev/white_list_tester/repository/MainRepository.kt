package com.awrdev.white_list_tester.repository

import android.R
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.awrdev.white_list_tester.ui.theme.GreenBasic
import com.awrdev.white_list_tester.ui.theme.RedBasic
import com.awrdev.white_list_tester.ui.theme.YellowBasic
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.ranges.contains

object MainRepository {
    val savedStatuses = mutableStateMapOf<String, String>()
    val currentLevel = mutableIntStateOf(0)
    val lastTimeOfCheck = mutableStateOf(LocalDateTime.now())

    val whiteListStatus =  mutableStateOf("Not checked yet")
    val RussiaStatus =  mutableStateOf("Not checked yet")
    val ForeignStatus =  mutableStateOf("Not checked yet")
    val BannedStatus =  mutableStateOf("Not checked yet")

    val isSMSAllowed = mutableStateOf(false)
    val isAirAlertActive = mutableStateOf(false)

    fun updateOrAddResourceStatus(resourceUrl: String, newStatus: String){
        savedStatuses[resourceUrl] = newStatus
    }
    fun updateOrSetLevel(level: Int){
        currentLevel.intValue = level
    }
    fun getCurrentStatus(): String{
        when(currentLevel.intValue){
            0 -> return "Не проверено"
            1 -> return "Белый список"
            2 -> return "Россия"
            3 -> return "Норм"
            4 -> return "Оттепель!"
            else -> return "N/A"
        }
    }

    fun getCurrentStatusDetailed(): String{
        when(currentLevel.intValue){
            0 -> return "Запустите проверку, чтобы узнать актуальный статус"
            1 -> return "Доступны только самые необходимые ресурсы"
            2 -> return "Возникли трудности с получением данных с зарубежных сайтов"
            3 -> return "В целом всё ок"
            4 -> return "Доступны все сайты!"
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

    fun getCurrentStatusColor(): Color{
        when(currentLevel.intValue){
            0 -> return RedBasic
            1 -> return RedBasic
            2 -> return YellowBasic
            3 -> return GreenBasic
            4 -> return Color.Cyan
            else -> return RedBasic
        }
    }

    fun updateOrSetLasTimeOfCheck(newTimeOfCheck: LocalDateTime){
        lastTimeOfCheck.value = newTimeOfCheck
    }

    fun getReadableTime(time: LocalDateTime): String{
        val diff = Duration.between(LocalDateTime.now(), time)
        return when(diff.toMinutes()){
            in 0 .. 1 -> "Now"
            else -> MainRepository.lastTimeOfCheck.value.truncatedTo(ChronoUnit.MINUTES).format(DateTimeFormatter.ISO_TIME)
        }
    }

    fun updateListStatus(listName: String, status: String){
        when(listName){
            "WL" -> whiteListStatus.value = status
            "RU" -> RussiaStatus.value = status
            "WWW" -> ForeignStatus.value = status
            "BAN" -> BannedStatus.value = status
            else -> {Log.e("AWR-RepoUpdate", "Unknown list name")}
        }
    }

    fun getSMSStatusColor(): Color{
        when(isSMSAllowed.value){
            true -> return GreenBasic
            false -> return YellowBasic
        }
    }

    fun getAirAlertColor(): Color{
        when(isAirAlertActive.value){
            true -> return YellowBasic
            false -> return GreenBasic
        }
    }


    fun getAirAlertIcon(): Color{
        when(isSMSAllowed.value){
            true -> return GreenBasic
            false -> return YellowBasic
        }
    }
}