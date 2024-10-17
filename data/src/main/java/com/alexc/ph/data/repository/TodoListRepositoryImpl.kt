package com.alexc.ph.data.repository

import com.alexc.ph.data.db.dao.TodoListDao
import com.alexc.ph.data.db.model.toTodoEntity
import com.alexc.ph.data.db.model.toTodoItem
import com.alexc.ph.domain.model.TodoItem
import com.alexc.ph.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class TodoListRepositoryImpl @Inject constructor(
    private val todoListDao: TodoListDao
): TodoListRepository {
    override val myTodoList: Flow<List<TodoItem>> = todoListDao.getAllTodoList().map {
        it.map { todoEntity -> todoEntity.toTodoItem() }
    }

    override suspend fun add(todoEntity: TodoItem) {
        todoListDao.insertTodo(todoEntity.toTodoEntity())
    }

    override suspend fun update(todoEntity: TodoItem) {
        todoListDao.updateTodo(todoEntity.toTodoEntity())
    }

    override suspend fun updateTodoItems(todoEntities: List<TodoItem>) {
        val todos = todoEntities.map { it.toTodoEntity() }
        todoListDao.updateTodoItems(todos)
    }

    override suspend fun delete(todoEntity: TodoItem) {
        todoListDao.deleteTodo(todoEntity.toTodoEntity())
    }
}