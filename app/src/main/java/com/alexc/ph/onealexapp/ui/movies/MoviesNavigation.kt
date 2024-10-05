package com.alexc.ph.onealexapp.ui.movies

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alexc.ph.data.model.movies.Movie
import kotlinx.serialization.Serializable

@Serializable data object MoviesRoute

fun NavController.navigateToMovies(navOptions: NavOptions) =
    navigate(route = MoviesRoute, navOptions)

fun NavGraphBuilder.moviesScreen(navigateToMovieDetails: (Movie) -> Unit = {}) {
    composable<MoviesRoute> {
        MoviesScreen(navigateToMovieDetails = navigateToMovieDetails)
    }
}