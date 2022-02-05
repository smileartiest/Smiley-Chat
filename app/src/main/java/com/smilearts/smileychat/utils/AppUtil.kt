package com.smilearts.smileychat.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.text.SimpleDateFormat
import java.util.*

class AppUtil {

    private var calendar: Calendar? = null
    private var simpleDateFormat: SimpleDateFormat? = null

    val scope = CoroutineScope(SupervisorJob())

    fun getDate() : String {
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd LLL yyyy")
        return simpleDateFormat?.format(calendar?.time).toString()
    }

    fun getTime() : String {
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        return simpleDateFormat?.format(calendar?.time).toString()
    }

    fun getTimeStr() : String {
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("HHmmss")
        return simpleDateFormat?.format(calendar?.time).toString()
    }

    fun getCountryCode(code: String) : String {
        return when(code) {
            "UAE" -> { CountryName.UAE }
            else -> { CountryName.INDIA }
        }
    }

}