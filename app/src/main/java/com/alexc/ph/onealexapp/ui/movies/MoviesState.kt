package com.alexc.ph.onealexapp.ui.movies

import com.alexc.ph.domain.model.CombinedMoviesAndSeries

data class MoviesState(
    val content: CombinedMoviesAndSeries,
    val isLoading: Boolean = false,
    val error: String = ""
)