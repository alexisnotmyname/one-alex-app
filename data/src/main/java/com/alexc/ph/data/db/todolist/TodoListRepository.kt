package com.alexc.ph.data.db.todolist

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TodoListRepository {
    val myTodoList: Flow<List<TodoEntity>>
    suspend fun add(todoEntity: TodoEntity)
    suspend fun update(todoEntity: TodoEntity)
    suspend fun delete(todoEntity: TodoEntity)
}

class TodoListRepositoryImpl @Inject constructor(
    private val todoListDao: TodoListDao
): TodoListRepository {
    override val myTodoList: Flow<List<TodoEntity>> = todoListDao.getAllTodoList()

    override suspend fun add(todoEntity: TodoEntity) {
        todoListDao.insertTodo(todoEntity)
    }

    override suspend fun update(todoEntity: TodoEntity) {
        todoListDao.updateTodo(todoEntity)
    }

    override suspend fun delete(todoEntity: TodoEntity) {
        todoListDao.deleteTodo(todoEntity)
    }

}