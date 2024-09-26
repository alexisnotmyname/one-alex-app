package com.alexc.ph.data.db.todolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val isDone: Boolean = false,
    val order: Int
)

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