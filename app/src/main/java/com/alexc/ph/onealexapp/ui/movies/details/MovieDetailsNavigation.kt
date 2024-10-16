package com.alexc.ph.onealexapp.ui.movies.details

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {
        MovieDetailsScreen(
            navigateBack = navigateBack,
            onWatchClick = onWatchClick
        )
    }
}