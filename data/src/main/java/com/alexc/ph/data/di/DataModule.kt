package com.alexc.ph.data.di

import com.alexc.ph.data.db.repository.TodoListRepository
import com.alexc.ph.data.db.repository.TodoListRepositoryImpl
import com.alexc.ph.data.network.retrofit.MoviesRepository
import com.alexc.ph.data.network.retrofit.MoviesRepositoryImpl
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

    @Binds
    abstract fun bindMoviesRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository
}