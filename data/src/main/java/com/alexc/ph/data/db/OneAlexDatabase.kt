package com.alexc.ph.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexc.ph.data.db.todolist.TodoEntity
import com.alexc.ph.data.db.todolist.TodoListDao

@Database(entities = [TodoEntity::class], version = 1)
abstract class OneAlexDatabase: RoomDatabase() {
    abstract fun todoListDao(): TodoListDao
}