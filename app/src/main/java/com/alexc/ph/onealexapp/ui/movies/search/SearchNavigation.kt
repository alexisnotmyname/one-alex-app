package com.alexc.ph.onealexapp.ui.movies.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alexc.ph.domain.model.BaseContent
import kotlinx.serialization.Serializable

@Serializable
data object SearchScreenRoute

fun NavController.navigateToSearch(navOptions: NavOptions? = null) =
    navigate(route = SearchScreenRoute, navOptions)

fun NavGraphBuilder.searchScreen(
    navigateBack: () -> Unit,
    navigateToDetails: (BaseContent) -> Unit
) {
    composable<SearchScreenRoute> {
        SearchScreenRoot(
            navigateBack = navigateBack,
            navigateToDetails = navigateToDetails
        )
    }
}