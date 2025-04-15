package com.alexc.ph.domain.model

data class Movie(
    override val id: Int,
    override val title: String,
    override val posterPath: String = "",
    override val backdropPath: String = "",
    override val contentType: ContentType = ContentType.MOVIE,
    override val genres: List<Genre> = emptyList(),
    val video: Boolean = false,
    val adult: Boolean = false,
    val overview: String = "",
    val popularity: Double = .0,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val releaseDate: String = "",
    val voteAverage: Double = .0,
    val voteCount: Int = 0,
    val runTime: Int = 0,
) : BaseContent()









