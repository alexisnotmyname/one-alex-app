package com.alexc.ph.onealexapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.alexc.ph.onealexapp.R
import com.alexc.ph.onealexapp.ui.components.OneAlexAppIcons
import com.alexc.ph.onealexapp.ui.movies.MoviesRoute
import com.alexc.ph.onealexapp.ui.todolist.TodoListRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>
) {
    TODO_LIST(
        selectedIcon = OneAlexAppIcons.List,
        unselectedIcon = OneAlexAppIcons.ListBorder,
        iconTextId = R.string.todo,
        titleTextId = R.string.todo,
        route = TodoListRoute::class,
    ),
    MOVIES(
        selectedIcon = OneAlexAppIcons.LiveTv,
        unselectedIcon = OneAlexAppIcons.LiveTvBorder,
        iconTextId = R.string.movies,
        titleTextId = R.string.movies,
        route = MoviesRoute::class,
    ),
}