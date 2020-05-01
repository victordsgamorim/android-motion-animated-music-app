package com.victor.musicapp.data.util

import java.util.*

fun Long.isExpired(): Boolean {
    val result = Calendar.getInstance().timeInMillis - this

    if (result < 3600000) {
        return false
    }
    return true
}