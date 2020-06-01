package com.victor.musicapp.presenter.util

import kotlin.math.abs

fun Long.convertPopularityToDouble(): String {
    val popularity = abs(this / 2).toString()
    val map = popularity.map { it.toString().toInt() }

    var decimal = 0

    if (map.size > 1) {
        decimal = map[0]
    }

    val unit = map[1]

    return "$decimal,$unit"
}