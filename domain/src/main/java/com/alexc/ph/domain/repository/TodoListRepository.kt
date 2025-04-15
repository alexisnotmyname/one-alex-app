package com.alexc.ph.domain.repository

import com.alexc.ph.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoListRepository {
    val myTodoList: Flow<List<TodoItem>>
    suspend fun add(todoEntity: TodoItem)
    suspend fun update(todoEntity: TodoItem)
    suspend fun updateTodoItems(todoList: List<TodoItem>)
    suspend fun delete(todoEntity: TodoItem)
    suspend fun search(searchQuery: String): Flow<List<TodoItem>>
}