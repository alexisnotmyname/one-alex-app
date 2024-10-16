package com.alexc.ph.onealexapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.alexc.ph.domain.model.BaseContent
import com.alexc.ph.domain.model.Category
import com.alexc.ph.onealexapp.ui.OneAlexAppState
import com.alexc.ph.onealexapp.ui.movies.category.pagedListScreen
import com.alexc.ph.onealexapp.ui.movies.details.movieDetailsScreen
import com.alexc.ph.onealexapp.ui.movies.moviesScreen

import com.alexc.ph.onealexapp.ui.todolist.TodoListRoute
import com.alexc.ph.onealexapp.ui.todolist.todoListScreen

@Composable
fun OneAlexNavHost(
    modifier: Modifier = Modifier,
    navigateToMovieDetails: (BaseContent) -> Unit = { _ -> },
    navigateToPagedList: (Category) -> Unit = {},
    navigateBack: () -> Unit = {},
    onWatchClick: (title: String) -> Unit = {},
    appState: OneAlexAppState
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = TodoListRoute,
        modifier = modifier
    ) {
        todoListScreen()
        moviesScreen(navigateToMovieDetails = navigateToMovieDetails, navigateToPagedList = navigateToPagedList)
        movieDetailsScreen(navigateBack = navigateBack, onWatchClick = onWatchClick)
        pagedListScreen(navigateBack = navigateBack, navigateToMovieDetails = navigateToMovieDetails)
    }
}