package com.alexc.ph.domain.model

import com.alexc.ph.domain.model.Movies.Movie

data class CombinedMovies(
    val nowPlaying: List<Movie>,
    val popular: List<Movie>
)