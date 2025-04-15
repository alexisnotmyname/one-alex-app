package com.alexc.ph.onealexapp.ui.movies

import com.alexc.ph.domain.model.AllMovies
import com.alexc.ph.domain.model.AllTvSeries

data class MoviesState(
    val movies: AllMovies = AllMovies(popular = emptyList(), nowPlaying = emptyList()),
    val tvSeries: AllTvSeries = AllTvSeries(popular = emptyList(), topRated = emptyList()),
    val isLoading: Boolean = true,
    val error: String = ""
)