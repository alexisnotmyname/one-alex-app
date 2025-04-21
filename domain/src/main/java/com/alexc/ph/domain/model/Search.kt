package com.alexc.ph.domain.model

data class Search(
    val id: Int,
    val name: String = "",
    val title: String = "",
    val originalName: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val mediaType: String = "",
    val adult: Boolean = false,
    val originalLanguage: String = "",
    val genres: List<Genre> = emptyList(),
    val popularity: Double = .0,
    val firstAirDate: String = "",
    val voteAverage: Double = .0,
    val voteCount: Int = 0
)