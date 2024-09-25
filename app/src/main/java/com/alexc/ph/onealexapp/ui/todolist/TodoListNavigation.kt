package com.alexc.ph.onealexapp.ui.todolist

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable data object TodoListRoute

fun NavController.navigateToTodoList(navOptions: NavOptions) =
    navigate(route = TodoListRoute, navOptions)

fun NavGraphBuilder.todoListScreen(modifier: Modifier = Modifier) {
    composable<TodoListRoute> {
        MyTodoListScreen(modifier = modifier)
    }
}