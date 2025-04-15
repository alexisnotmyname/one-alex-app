package com.alexc.ph.onealexapp.ui.todolist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable data object TodoListRoute

fun NavController.navigateToTodoList(navOptions: NavOptions) =
    navigate(route = TodoListRoute, navOptions)

fun NavGraphBuilder.todoListScreen() {
    composable<TodoListRoute> {
        TodoListScreenRoot()
    }
}