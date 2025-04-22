package com.alexc.ph.domain.model

class CombinedMoviesAndTvSeries(
    val movies: AllMovies,
    val tvSeries: AllTvSeries
)

data class AllMovies(
    val nowPlaying: List<Movie>,
    val popular: List<Movie>
)

data class AllTvSeries(
    val topRated: List<TvSeries>,
    val popular: List<TvSeries>
)