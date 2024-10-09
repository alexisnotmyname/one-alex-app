package com.alexc.ph.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val dates: Dates? = null,
    val page: Int,
    @SerialName("results") val movies: List<Movie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
) {

    @Serializable
    data class Movie(
        val id: Int,
        val title: String,
        val video: Boolean = false,
        val adult: Boolean = false,
        val overview: String = "",
        val popularity: Double = .0,
        @SerialName("genre_ids") val genreIds: List<Int> = emptyList(),
        @SerialName("original_language") val originalLanguage: String = "",
        @SerialName("original_title") val originalTitle: String = "",
        @SerialName("backdrop_path")  val backDropPath: String = "",
        @SerialName("poster_path") val posterPath: String = "",
        @SerialName("release_date") val releaseDate: String = "",
        @SerialName("vote_average") val voteAverage: Double = .0,
        @SerialName("vote_count") val voteCount: Int = 0
    )

    @Serializable
    data class Dates(
        val maximum: String,
        val minimum: String
    )
}
