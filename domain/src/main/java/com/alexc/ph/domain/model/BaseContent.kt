package com.alexc.ph.domain.model

abstract class BaseContent {
    abstract val id: Int
    abstract val title: String
    abstract val posterPath: String
    abstract val backdropPath: String
    abstract val contentType: ContentType
}

enum class ContentType(val value: String){
    MOVIE("movie"),
    TV("tv")
}

enum class Category(val value: String) {
    NOW_PLAYING_MOVIES("Now Playing Movies"),
    POPULAR_MOVIES("Popular Movies"),
    POPULAR_TV_SERIES("Popular TV Series"),
    TOP_RATED_TV_SERIES("Top Rated TV Series"),
}