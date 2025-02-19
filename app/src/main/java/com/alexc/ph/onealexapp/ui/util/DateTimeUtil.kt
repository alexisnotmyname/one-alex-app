package com.alexc.ph.onealexapp.ui.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

fun convertMillisToDate(millis: Long, format: String = "E, MMM dd"): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(Date(millis))
}

fun getCurrentDayMillis(): Long {
    val today = LocalDate.now()
    return today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
