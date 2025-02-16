package com.alexc.ph.domain.model

data class TodoItem(
    val id: Int = 0,
    val title: String,
    val isDone: Boolean = false,
    val order: Int,
    val dateTimeCreated: Long = System.currentTimeMillis(),
    val dateTimeDue: Long? = null,
    val dateTimeCompleted: Long? = null
)

