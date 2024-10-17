package com.alexc.ph.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alexc.ph.data.db.model.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {
    @Query("SELECT * FROM todoentity order by `isDone` ASC, `order` ASC")
    fun getAllTodoList(): Flow<List<TodoEntity>>

    @Insert
    suspend fun insertTodo(todo: TodoEntity)

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    @Update
    suspend fun updateTodoItems(todos: List<TodoEntity>)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)
}