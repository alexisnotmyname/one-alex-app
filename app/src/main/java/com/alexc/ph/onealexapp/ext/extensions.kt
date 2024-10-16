package com.alexc.ph.onealexapp.ext

fun List<Any>.toSeasonString(): String {
    val seasonCount = this.size
    return if (seasonCount == 1) {
        "$seasonCount season"
    } else {
        "$seasonCount seasons"
    }
}