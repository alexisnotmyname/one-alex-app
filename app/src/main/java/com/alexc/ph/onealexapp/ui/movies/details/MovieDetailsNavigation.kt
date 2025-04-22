package com.alexc.ph.onealexapp.ui.movies.details

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alexc.ph.domain.model.ContentType
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(val id: Int, val contentType: ContentType)

fun NavController.navigateToMovieDetails(id: Int, contentType: ContentType, navOptions: NavOptions? = null) =
    navigate(route = MovieDetailsRoute(id, contentType), navOptions)

fun NavGraphBuilder.movieDetailsScreen(
    navigateBack: () -> Unit,
    onWatchClick: (title: String) -> Unit
) {
    composable<MovieDetailsRoute>(
        enterTransition = { slideInHorizontally { initialOffset ->
            initialOffset
        } },
        exitTransition = { slideOutHorizontally { initialOffset ->
            initialOffset
        } }
    ) {
        MovieDetailsScreenRoot(
            navigateBack = navigateBack,
            onWatchClick = onWatchClick
        )
    }
}