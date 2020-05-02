package com.victor.musicapp.data.util

import java.util.*

fun Long.isExpired(): Boolean {
    val timeDiff = Calendar.getInstance().timeInMillis - this

    if (timeDiff < ONE_HOUR_IN_MILLIS) {
        return false
    }
    return true
}