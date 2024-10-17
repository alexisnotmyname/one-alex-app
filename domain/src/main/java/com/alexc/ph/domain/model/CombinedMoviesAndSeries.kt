package com.alexc.ph.domain.model

class CombinedMoviesAndSeries(
    val movies: CombinedMovies,
    val tvSeries: CombinedTvSeries
)

data class CombinedMovies(
    val nowPlaying: List<Movie>,
    val popular: List<Movie>
)

data class CombinedTvSeries(
    val topRated: List<TvSeries>,
    val popular: List<TvSeries>
)