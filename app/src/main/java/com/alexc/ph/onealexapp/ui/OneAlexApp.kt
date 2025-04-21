package com.alexc.ph.onealexapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import com.alexc.ph.onealexapp.ui.components.GradientBackground
import com.alexc.ph.onealexapp.ui.movies.details.movieDetailsScreen
import com.alexc.ph.onealexapp.ui.movies.moviesScreen
import com.alexc.ph.onealexapp.ui.movies.paged.pagedListScreen
import com.alexc.ph.onealexapp.ui.movies.search.searchScreen
import com.alexc.ph.onealexapp.ui.navigation.OneAlexNavigationSuiteScaffold
import com.alexc.ph.onealexapp.ui.theme.LocalGradientColors
import com.alexc.ph.onealexapp.ui.todolist.TodoListRoute
import com.alexc.ph.onealexapp.ui.todolist.todoListScreen
import kotlin.reflect.KClass

@Composable
fun OneAlexApp(
    modifier: Modifier = Modifier,
    appState: OneAlexAppState,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    val currentDestination = appState.currentDestination
    OneAlexNavigationSuiteScaffold(
        navigationSuiteItems = {
            appState.topLevelDestinations.forEach { destination ->
                val selected = currentDestination.isRouteInHierarchy(destination.route)
                item(
                    selected = selected,
                    onClick = { appState.navigateToTopLevelDestination(topLevelDestination = destination) },
                    icon = {
                        Icon(
                            imageVector = destination.unselectedIcon,
                            contentDescription = null,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destination.selectedIcon,
                            contentDescription = null,
                        )
                    },
                    label = { Text(stringResource(destination.iconTextId)) },
                    modifier = Modifier
                )
            }
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
    ) {
        GradientBackground(
            gradientColors = LocalGradientColors.current
        ) {
            Scaffold(
                modifier = modifier,
                containerColor = Color.Transparent,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
            ) { innerPadding ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    Box(
                        modifier = Modifier.consumeWindowInsets(
//                            if (shouldShowTopAppBar) {
                                WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
//                            } else {
//                                WindowInsets(0, 0, 0, 0)
//                            },
                        ),
                    ) {
                        val navController = appState.navController
                        NavHost(
                            navController = navController,
                            startDestination = TodoListRoute,
                            modifier = modifier
                        ) {
                            todoListScreen()
                            moviesScreen(
                                navigateToMovieDetails = appState::navigateToMovieDetails,
                                navigateToPagedList = appState::navigateToPagedList,
                                navigateToSearch = appState::navigateToSearch
                            )
                            movieDetailsScreen(
                                navigateBack = appState::navigateBack,
                                onWatchClick = appState::watch
                            )
                            pagedListScreen(
                                navigateBack = appState::navigateBack,
                                navigateToMovieDetails = appState::navigateToMovieDetails
                            )
                            searchScreen(
                                navigateBack = appState::navigateBack
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false