package com.alexc.ph.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val id: Int,
    val title: String,
    val video: Boolean = false,
    val adult: Boolean = false,
    val overview: String = "",
    val popularity: Double = .0,
    @SerialName("genres") val genres: List<GenreResponse> = emptyList(),
    @SerialName("original_language") val originalLanguage: String = "",
    @SerialName("original_title") val originalTitle: String = "",
    @SerialName("backdrop_path")  val backdropPath: String = "",
    @SerialName("poster_path") val posterPath: String = "",
    @SerialName("release_date") val releaseDate: String = "",
    @SerialName("vote_average") val voteAverage: Double = .0,
    @SerialName("vote_count") val voteCount: Int = 0,
    @SerialName("runtime") val runTime: Int = 0
)