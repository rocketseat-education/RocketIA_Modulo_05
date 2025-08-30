package com.rocketseat.rocketia.domain.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Long.formatDatetime(locale: Locale = Locale.getDefault()): String {
    if (this <= 0) return ""

    val calendar = Calendar.getInstance().apply { timeInMillis = this@formatDatetime }
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", locale)

    return formatter.format(calendar.time)
}

fun Long.formatTime(locale: Locale = Locale.getDefault()): String {
    if (this <= 0) return ""

    val calendar = Calendar.getInstance().apply { timeInMillis = this@formatTime }
    val formatter = SimpleDateFormat("HH:mm", locale)

    return formatter.format(calendar.time)
}