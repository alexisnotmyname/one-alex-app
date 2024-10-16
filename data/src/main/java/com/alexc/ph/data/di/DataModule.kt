package com.alexc.ph.data.di

import com.alexc.ph.data.repository.MoviesRepository
import com.alexc.ph.data.repository.MoviesRepositoryImpl
import com.alexc.ph.data.repository.TodoListRepository
import com.alexc.ph.data.repository.TodoListRepositoryImpl
import com.alexc.ph.data.repository.TvSeriesRepository
import com.alexc.ph.data.repository.TvSeriesRepositoryImpl
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

    @Binds
    abstract fun bindTvSeriesRepository(
        tvSeriesRepositoryImpl: TvSeriesRepositoryImpl
    ): TvSeriesRepository
}