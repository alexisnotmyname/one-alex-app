package com.alexc.ph.onealexapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.alexc.ph.domain.model.BaseContent
import com.alexc.ph.domain.model.Category
import com.alexc.ph.onealexapp.ui.movies.category.navigateToPagedList
import com.alexc.ph.onealexapp.ui.movies.details.navigateToMovieDetails
import com.alexc.ph.onealexapp.ui.movies.navigateToMovies
import com.alexc.ph.onealexapp.ui.navigation.TopLevelDestination
import com.alexc.ph.onealexapp.ui.todolist.navigateToTodoList


@Composable
fun rememberOneAlexAppState(
    navController: NavHostController = rememberNavController(),
): OneAlexAppState {
    return remember(
        navController,
    ) {
        OneAlexAppState(
            navController = navController
        )
    }
}

class OneAlexAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) ?: false
            }
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when(topLevelDestination) {
            TopLevelDestination.TODO_LIST -> navController.navigateToTodoList(topLevelNavOptions)
            TopLevelDestination.MOVIES -> navController.navigateToMovies(topLevelNavOptions)
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateToMovieDetails(content: BaseContent) {
        navController.navigateToMovieDetails(content.id, content.contentType)
    }

    fun navigateToPagedList(category: Category) {
        navController.navigateToPagedList(category)
    }

    fun watch(title: String) {
        // TODO redirect to link
    }
}
