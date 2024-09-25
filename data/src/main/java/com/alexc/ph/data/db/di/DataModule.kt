package com.alexc.ph.data.db.di

import com.alexc.ph.data.db.todolist.TodoListRepository
import com.alexc.ph.data.db.todolist.TodoListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsTodoListRepository(
        todoListRepository: TodoListRepositoryImpl
    ): TodoListRepository
}