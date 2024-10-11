package com.alexc.ph.domain.model

import com.alexc.ph.data.network.model.MoviesResponse
import com.alexc.ph.domain.model.Movies.Movie

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

fun MoviesResponse.toMoviesDomain() = Movies(
    movies = this.movies.map { movie ->
        Movie(
            id = movie.id,
            title = movie.title,
            video = movie.video,
            adult = movie.adult,
            overview = movie.overview,
            popularity = movie.popularity,
            genreIds = movie.genreIds,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            backDropPath = movie.backDropPath,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount
        )
    },
    page = this.page,
    totalPages = this.totalPages,
    totalResults = this.totalResults
)
