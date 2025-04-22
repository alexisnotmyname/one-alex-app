package com.alexc.ph.data.network.model

import com.alexc.ph.domain.model.ContentType.*
import com.alexc.ph.domain.model.Search
import com.alexc.ph.domain.util.backdropImageUrl
import com.alexc.ph.domain.util.posterImageUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val id: Int,
    val name: String = "",
    val title: String = "",
    @SerialName("original_name") val originalName: String = "",
    val overview: String = "",
    @SerialName("poster_path") val posterPath: String = "",
    @SerialName("backdrop_path") val backdropPath: String = "",
    @SerialName("profile_path") val profilePath: String = "",
    @SerialName("media_type") val mediaTypeResponse: MediaTypeResponse = MediaTypeResponse.MOVIE,
    val adult: Boolean = false,
    @SerialName("original_language") val originalLanguage: String = "",
    val popularity: Double = .0,
    @SerialName("first_air_date") val firstAirDate: String = "",
    @SerialName("vote_average") val voteAverage: Double = .0,
    @SerialName("vote_count") val voteCount: Int = 0
)

fun SearchResponse.toSearch() = Search(
    id = this.id,
    name = this.name,
    title = this.title,
    originalName = this.originalName,
    overview = this.overview,
    posterPath = this.posterPath.posterImageUrl(),
    backdropPath = this.backdropPath.backdropImageUrl(),
    profilePath = this.profilePath.posterImageUrl(),
    contentType = when(this.mediaTypeResponse) {
        MediaTypeResponse.TV -> TV
        MediaTypeResponse.MOVIE -> MOVIE
        MediaTypeResponse.PERSON -> PERSON
    },
    adult = this.adult,
    originalLanguage = this.originalLanguage,
    popularity = this.popularity,
    firstAirDate = this.firstAirDate,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
    genres = emptyList(),
)
