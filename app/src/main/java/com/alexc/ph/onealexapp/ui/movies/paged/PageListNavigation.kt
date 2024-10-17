package com.alexc.ph.onealexapp.ui.movies.paged

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
import com.alexc.ph.domain.model.BaseContent
import com.alexc.ph.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class PagedListRoute(val category: Category)

fun NavController.navigateToPagedList(category: Category, navOptions: NavOptions? = null) =
    navigate(route = PagedListRoute(category), navOptions)

fun NavGraphBuilder.pagedListScreen(
    navigateBack: () -> Unit,
    navigateToMovieDetails: (BaseContent) -> Unit = {},
) {
    composable<PagedListRoute>(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {
        PagedListScreen(
            navigateBack = navigateBack,
            navigateToMovieDetails = navigateToMovieDetails
        )
    }
}