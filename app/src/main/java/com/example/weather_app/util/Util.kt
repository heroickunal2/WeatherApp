package com.example.weather_app.util

import java.text.SimpleDateFormat
import java.util.*


object Util {

    fun parseDateToDay(date: String?): String {
        //Output -> Sun
        val outputPattern = "E"

        return if (date.isNullOrEmpty()) {
            "NA"
        } else {
            return parseDates(date, outputPattern)
        }
    }


    fun parseDates(dateString: String?, outputPattern: String): String {
        val inputPattern = "yyyy-MM-dd"
        val inputFormat = SimpleDateFormat(inputPattern, Locale.ENGLISH)
        val outputFormat = SimpleDateFormat(outputPattern, Locale.ENGLISH)
        return if (dateString.isNullOrEmpty()) {
            "NA"
        } else {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date)
        }
    }
}
