package com.alexc.ph.onealexapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.alexc.ph.onealexapp.ui.OneAlexAppState
import com.alexc.ph.onealexapp.ui.budget.budgetTrackerScreen
import com.alexc.ph.onealexapp.ui.movies.details.movieDetailsScreen
import com.alexc.ph.onealexapp.ui.movies.details.navigateToMovieDetails
import com.alexc.ph.onealexapp.ui.movies.moviesScreen
import com.alexc.ph.onealexapp.ui.todolist.TodoListRoute
import com.alexc.ph.onealexapp.ui.todolist.todoListScreen

@Composable
fun OneAlexNavHost(
    modifier: Modifier = Modifier,
    appState: OneAlexAppState
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = TodoListRoute,
        modifier = modifier
    ) {
        todoListScreen()
        budgetTrackerScreen()
        moviesScreen(navigateToMovieDetails = {
            navController.navigateToMovieDetails()
        })
        movieDetailsScreen()
    }
}