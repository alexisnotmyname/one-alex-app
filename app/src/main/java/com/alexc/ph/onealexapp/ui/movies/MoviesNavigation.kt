package com.alexc.ph.onealexapp.ui.movies

import androidx.compose.animation.slideInHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alexc.ph.domain.model.BaseContent
import com.alexc.ph.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable data object MoviesRoute

fun NavController.navigateToMovies(navOptions: NavOptions) =
    navigate(route = MoviesRoute, navOptions)

fun NavGraphBuilder.moviesScreen(
    navigateToMovieDetails: (BaseContent) -> Unit = {},
    navigateToPagedList: (Category) -> Unit = {},
    navigateToSearch: () -> Unit = {}
) {
    composable<MoviesRoute>(
        popEnterTransition = { slideInHorizontally() }
    ) {
        MoviesScreenRoot(
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToPagedList = navigateToPagedList,
            navigateToSearch = navigateToSearch
        )
    }
}