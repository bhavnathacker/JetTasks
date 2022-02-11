package com.bhavnathacker.jettasks.util

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDateToString(): String {
    val format = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
    return format.format(this)
}


fun Date.getDateWithoutTime(): Date {
    try {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.parse(formatter.format(this)) ?: this
    } catch (e: Exception) {
    }
    return this
}
