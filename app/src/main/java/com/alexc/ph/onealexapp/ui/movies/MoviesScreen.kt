package com.alexc.ph.onealexapp.ui.movies

import com.alexc.ph.onealexapp.ui.components.PageUnderConstructionScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    moviesMoviesViewModel: MoviesViewModel = hiltViewModel()
) {
    val moviesState by moviesMoviesViewModel.uiState.collectAsStateWithLifecycle()
    if (moviesState is MoviesUiState.Success) {
        val movies = (moviesState as MoviesUiState.Success).movies
        println(movies)
    }

    PageUnderConstructionScreen(modifier = modifier)
}