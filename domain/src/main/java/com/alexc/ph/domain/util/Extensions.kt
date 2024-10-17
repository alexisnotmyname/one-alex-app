package com.alexc.ph.domain.util

import com.alexc.ph.domain.model.Configuration

fun String?.backdropImageUrl(configuration: Configuration? = null): String {
    val baseUrl = configuration?.baseUrl ?: "https://image.tmdb.org/t/p/"
    val backdropSize = configuration?.imageSize ?: "original"
    return "$baseUrl$backdropSize$this"
}

fun String?.posterImageUrl(configuration: Configuration? = null): String {
    val baseUrl = configuration?.baseUrl ?: "https://image.tmdb.org/t/p/"
    val posterSize = configuration?.imageSize ?: "w342"
    return "$baseUrl$posterSize$this"
}