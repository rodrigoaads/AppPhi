package com.rodrigoads.appphi.utils

import java.text.NumberFormat
import java.util.*

fun Int.currencyFormat(): String {
    return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)
}