package com.alexc.ph.onealexapp.ui.movies.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MovieDetailsRoute

fun NavController.navigateToMovieDetails(navOptions: NavOptions? = null) =
    navigate(route = MovieDetailsRoute, navOptions)

fun NavGraphBuilder.movieDetailsScreen() {
    composable<MovieDetailsRoute> {
        MovieDetails()
    }
}