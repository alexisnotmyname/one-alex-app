package com.alexc.ph.data.di

import com.alexc.ph.data.repository.MoviesRepositoryImpl
import com.alexc.ph.data.repository.TodoListRepositoryImpl
import com.alexc.ph.data.repository.TvSeriesRepositoryImpl
import com.alexc.ph.domain.repository.MoviesRepository
import com.alexc.ph.domain.repository.TodoListRepository
import com.alexc.ph.domain.repository.TvSeriesRepository
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

    @Singleton
    @Binds
    abstract fun bindMoviesRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    @Singleton
    @Binds
    abstract fun bindTvSeriesRepository(
        tvSeriesRepositoryImpl: TvSeriesRepositoryImpl
    ): TvSeriesRepository
}