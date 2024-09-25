package com.alexc.ph.onealexapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.alexc.ph.onealexapp.ui.budget.navigateToBudgetTracker
import com.alexc.ph.onealexapp.ui.movies.navigateToMovies
import com.alexc.ph.onealexapp.ui.navigation.TopLevelDestination
import com.alexc.ph.onealexapp.ui.todolist.navigateToTodoList
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberOneAlexAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): OneAlexAppState {
    return remember(
        navController,
        coroutineScope
    ) {
        OneAlexAppState(
            navController = navController,
            coroutineScope = coroutineScope
        )
    }
}

class OneAlexAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope
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
            TopLevelDestination.BUDGET_TRACKER -> navController.navigateToBudgetTracker(topLevelNavOptions)
            TopLevelDestination.MOVIES -> navController.navigateToMovies(topLevelNavOptions)
        }
    }
}
