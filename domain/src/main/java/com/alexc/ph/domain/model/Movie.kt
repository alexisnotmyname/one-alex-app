package com.alexc.ph.domain.model

import com.alexc.ph.data.network.model.MovieResponse
import com.alexc.ph.domain.util.backdropImageUrl
import com.alexc.ph.domain.util.posterImageUrl

data class Movie(
    override val id: Int,
    override val title: String,
    override val posterPath: String = "",
    override val backdropPath: String = "",
    override val contentType: ContentType = ContentType.MOVIE,
    val video: Boolean = false,
    val adult: Boolean = false,
    val overview: String = "",
    val popularity: Double = .0,
    val genres: List<Genre> = emptyList(),
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val releaseDate: String = "",
    val voteAverage: Double = .0,
    val voteCount: Int = 0,
    val runTime: Int = 0
) : BaseContent()

fun MovieResponse.toMovie(configuration: Configuration? = null) = Movie(
    id = this.id,
    title = this.title,
    video = this.video,
    adult = this.adult,
    overview = this.overview,
    popularity = this.popularity,
    genres = this.genres.map { it.toGenre() },
    originalLanguage = this.originalLanguage,
    originalTitle = this.originalTitle,
    backdropPath = this.backdropPath.backdropImageUrl(configuration),
    posterPath = this.posterPath.posterImageUrl(configuration),
    releaseDate = this.releaseDate,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
    runTime = this.runTime,
)







