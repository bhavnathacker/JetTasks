package com.bhavnathacker.jettasks.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: Date): String {
    val format = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
    return format.format(date)
}
