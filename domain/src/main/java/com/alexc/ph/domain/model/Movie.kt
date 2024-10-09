package com.alexc.ph.domain.model

data class Movies(
    val dates: Dates? = null,
    val page: Int,
    val movies: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
) {
    data class Movie(
        val id: Int,
        val title: String,
        val video: Boolean = false,
        val adult: Boolean = false,
        val overview: String = "",
        val popularity: Double = .0,
        val genreIds: List<Int> = emptyList(),
        val originalLanguage: String = "",
        val originalTitle: String = "",
        val backDropPath: String = "",
        val posterPath: String = "",
        val releaseDate: String = "",
        val voteAverage: Double = .0,
        val voteCount: Int = 0
    )
    data class Dates(
        val maximum: String,
        val minimum: String
    )
}
