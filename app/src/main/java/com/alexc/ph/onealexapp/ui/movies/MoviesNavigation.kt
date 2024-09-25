package com.alexc.ph.onealexapp.ui.movies

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable data object MoviesRoute

fun NavController.navigateToMovies(navOptions: NavOptions) =
    navigate(route = MoviesRoute, navOptions)

fun NavGraphBuilder.moviesScreen(modifier: Modifier = Modifier) {
    composable<MoviesRoute> {
        MoviesScreen(modifier = modifier)
    }
}