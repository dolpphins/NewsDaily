package com.mtan.newsdaily.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    val SECOND = 1000
    val MINUTE = 60 * SECOND
    val HOUR = 60 * MINUTE
    val DAY = 24 * HOUR

    fun getTodayStr(): String {
        val date = Date(System.currentTimeMillis())
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(date)
    }

    fun getYesterdayStr(): String {
        val date = Date(System.currentTimeMillis() - DAY)
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(date)
    }

    fun getDayStr(time: Long): String {
        val date = Date(time)
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(date)
    }
}