package com.alexc.ph.onealexapp.di

import com.alexc.ph.domain.GetMovieAndTvSeriesUseCase
import com.alexc.ph.domain.GetMovieDetailsUseCase
import com.alexc.ph.domain.GetPagedContentUseCase
import com.alexc.ph.domain.SearchUseCase
import com.alexc.ph.onealexapp.ui.movies.MoviesViewModel
import com.alexc.ph.onealexapp.ui.movies.details.MovieDetailsViewModel
import com.alexc.ph.onealexapp.ui.movies.paged.PagedListViewModel
import com.alexc.ph.onealexapp.ui.movies.search.SearchViewModel
import com.alexc.ph.onealexapp.ui.todolist.TodoListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { TodoListViewModel(get()) }
    viewModel { MoviesViewModel(get()) }
    viewModel { PagedListViewModel(get(), get()) }
    viewModel { MovieDetailsViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
}

val useCaseModule = module {
    factory { GetMovieAndTvSeriesUseCase(get(), get()) }
    factory { GetPagedContentUseCase(get(), get()) }
    factory { GetMovieDetailsUseCase(get(), get()) }
    factory { SearchUseCase(get()) }
}