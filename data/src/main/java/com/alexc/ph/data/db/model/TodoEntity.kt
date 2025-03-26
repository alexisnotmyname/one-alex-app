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
    val order: Int,
    val dateTimeCreated: Long = System.currentTimeMillis(),
    val dateTimeDue: Long? = null,
    val dateTimeCompleted: Long? = null,
)

fun TodoItem.toTodoEntity() = TodoEntity(id = id, title = title, isDone = isDone, order = order, dateTimeCreated = dateTimeCreated, dateTimeDue = dateTimeDue, dateTimeCompleted = dateTimeCompleted)
fun TodoEntity.toTodoItem() = TodoItem(id = id, title = title, isDone = isDone, order = order, dateTimeCreated = dateTimeCreated, dateTimeDue = dateTimeDue, dateTimeCompleted = dateTimeCompleted)

