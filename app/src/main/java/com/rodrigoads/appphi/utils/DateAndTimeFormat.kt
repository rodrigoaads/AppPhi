package com.rodrigoads.appphi.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.dateAndTimeFormat(dateOnly: Boolean = false): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale("pt", "BR"))
    return try {
        val date = inputFormat.parse(this)!!
        if (dateOnly) {
            SimpleDateFormat("dd/MM", Locale("pt", "BR")).format(date)
        } else {
            SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale("pt", "BR")).format(date)
        }
    } catch (e: ParseException) {
        "Data não identificada"
    }
}