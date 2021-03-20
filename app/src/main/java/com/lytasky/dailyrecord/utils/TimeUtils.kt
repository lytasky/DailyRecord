package com.lytasky.dailyrecord.utils

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object TimeUtils {

    const val MSEC = 1
    const val SEC = 1000
    const val MIN = 60000
    const val HOUR = 3600000
    const val DAY = 86400000


    private val SDF_THREAD_LOCAL: ThreadLocal<MutableMap<String, SimpleDateFormat>> =
        object :
            ThreadLocal<MutableMap<String, SimpleDateFormat>>() {
            override fun initialValue(): MutableMap<String, SimpleDateFormat> {
                return mutableMapOf()
            }
        }

    private fun getDefaultFormat(): SimpleDateFormat {
        return getSafeDateFormat("yyyy-MM-dd HH:mm:ss")
    }

    @SuppressLint("SimpleDateFormat")
    fun getSafeDateFormat(pattern: String): SimpleDateFormat {
        val sdfMap: MutableMap<String, SimpleDateFormat> =
            SDF_THREAD_LOCAL.get()!!
        var simpleDateFormat = sdfMap!![pattern]
        if (simpleDateFormat == null) {
            simpleDateFormat = SimpleDateFormat(pattern)
            sdfMap[pattern] = simpleDateFormat
        }
        return simpleDateFormat
    }

    fun getNowMills(): Long {
        return System.currentTimeMillis()
    }

    fun getNowString(): String {
        return millis2String(System.currentTimeMillis(), getDefaultFormat())
    }

    fun getNowString(format: DateFormat): String {
        return millis2String(System.currentTimeMillis(), format)
    }

    private fun millis2String(millis: Long, format: DateFormat): String {
        return format.format(Date(millis))
    }

    fun getChineseWeek(date: Date?): String? {
        return SimpleDateFormat("E", Locale.CHINA).format(date)
    }

    fun millis2Date(millis: Long): Date? {
        return Date(millis)
    }
    fun isToday(millis: Long): Boolean {
        val wee: Long = getWeeOfToday()
        return millis >= wee && millis < wee + DAY
    }

    private fun getWeeOfToday(): Long {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
    }

    fun string2Millis(time: String): Long {
        return string2Millis(time, getDefaultFormat())
    }

    fun string2Millis(time: String, format: DateFormat): Long {
        try {
            return format.parse(time).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return -1
    }

    fun getMillisPlus8H(time: String): Long {
        return string2Millis(time) + 8 * HOUR;
    }

    fun getTimeStringByMillis(millis: Long, format: DateFormat): String {
        return millis2String(millis, format)
    }
}