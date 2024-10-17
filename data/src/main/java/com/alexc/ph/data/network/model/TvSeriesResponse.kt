package com.alexc.ph.data.network.model

import com.alexc.ph.domain.model.Configuration
import com.alexc.ph.domain.model.Season
import com.alexc.ph.domain.model.TvSeries
import com.alexc.ph.domain.util.backdropImageUrl
import com.alexc.ph.domain.util.posterImageUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TvSeriesResponse(
    val id: Int,
    val name: String,
    val overview: String = "",
    val popularity: Double = .0,
    val adult: Boolean = false,
    @SerialName("first_air_date") val firstAirDate: String = "",
    @SerialName("genres") val genres: List<GenreResponse> = emptyList(),
    @SerialName("origin_country") val originCountry: List<String> = emptyList(),
    @SerialName("original_language") val originalLanguage: String = "",
    @SerialName("original_name") val originalName: String = "",
    @SerialName("backdrop_path") val backdropPath: String = "",
    @SerialName("poster_path") val posterPath: String? = "",
    @SerialName("vote_average") val voteAverage: Double = .0,
    @SerialName("vote_count") val voteCount: Int = 0,
    @SerialName("seasons") val seasons: List<SeasonResponse> = emptyList(),
)


@Serializable
data class SeasonResponse(
    val id: Int,
    val name: String,
    val overview: String = "",
    @SerialName("air_date") val airDate: String = "",
    @SerialName("episode_count") val episodeCount: Int = 0,
    @SerialName("poster_path") val posterPath: String = "",
    @SerialName("season_number") val seasonNumber: Int = 0,
    @SerialName("vote_average") val voteAverage: Double = .0
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