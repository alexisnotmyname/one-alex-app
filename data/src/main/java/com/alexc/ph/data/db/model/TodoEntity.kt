package com.alexc.ph.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexc.ph.domain.model.TodoItem

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val isDone: Boolean = false,
    val order: Int
)

fun TodoItem.toTodoEntity() = TodoEntity(id = id, title = title, isDone = isDone, order = order)
fun TodoEntity.toTodoItem() = TodoItem(id = id, title = title, isDone = isDone, order = order)

