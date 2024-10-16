package com.alexc.ph.domain.model

import com.alexc.ph.data.network.model.SeasonResponse
import com.alexc.ph.data.network.model.TvSeriesResponse
import com.alexc.ph.domain.util.backdropImageUrl
import com.alexc.ph.domain.util.posterImageUrl

data class TvSeries(
    override val id: Int,
    override val title: String,
    override val posterPath: String = "",
    override val backdropPath: String = "",
    override val contentType: ContentType = ContentType.TV,
    val overview: String = "",
    val popularity: Double = .0,
    val adult: Boolean = false,
    val firstAirDate: String = "",
    val genres: List<Genre> = emptyList(),
    val originCountry: List<String> = emptyList(),
    val originalLanguage: String = "",
    val originalName: String = "",
    val voteAverage: Double = .0,
    val voteCount: Int = 0,
    val seasons: List<Season> = emptyList()
): BaseContent()

data class Season(
    val id: Int,
    val name: String ,
    val overview: String = "",
    val airDate: String = "",
    val episodeCount: Int = 0,
    val posterPath: String = "",
    val seasonNumber: Int = 0,
    val voteAverage: Double = .0
)

fun TvSeriesResponse.toTvSeries(configuration: Configuration? = null) = TvSeries(
    id = this.id,
    title = this.name,
    overview = this.overview,
    popularity = this.popularity,
    adult = this.adult,
    firstAirDate = this.firstAirDate,
    genres = this.genres.map { it.toGenre() },
    originCountry = this.originCountry,
    originalLanguage = this.originalLanguage,
    originalName = this.originalName,
    backdropPath = this.backdropPath.backdropImageUrl(configuration),
    posterPath = this.posterPath.posterImageUrl(configuration),
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
    seasons = this.seasons.map { it.toSeason() },
)

fun SeasonResponse.toSeason() = Season(
    id = this.id,
    name = this.name,
    overview = this.overview,
    airDate = this.airDate,
    episodeCount = this.episodeCount,
    posterPath = this.posterPath,
    seasonNumber = this.seasonNumber,
    voteAverage = this.voteAverage
)

