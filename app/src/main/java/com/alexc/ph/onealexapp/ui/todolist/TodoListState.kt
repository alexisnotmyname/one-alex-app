package com.alexc.ph.onealexapp.ui.todolist

import com.alexc.ph.domain.model.TodoItem

data class TodoListState(
    val todoList: List<TodoItem> = emptyList(),
    val searchQuery: String = "",
)