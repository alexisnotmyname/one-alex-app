package com.alexc.ph.domain.model

data class Search(
    override val id: Int,
    override val title: String = "",
    override val posterPath: String = "",
    override val backdropPath: String = "",
    override val contentType: ContentType,
    override val genres: List<Genre> = emptyList(),
    val name: String = "",
    val originalName: String = "",
    val overview: String = "",
    val profilePath: String = "",
    val adult: Boolean = false,
    val originalLanguage: String = "",
    val popularity: Double = .0,
    val firstAirDate: String = "",
    val voteAverage: Double = .0,
    val voteCount: Int = 0,
): BaseContent()