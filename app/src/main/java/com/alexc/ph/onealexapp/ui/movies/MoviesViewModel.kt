package com.alexc.ph.onealexapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexc.ph.domain.GetMovieAndTvSeriesUseCase
import com.alexc.ph.domain.model.CombinedMoviesAndSeries
import com.alexc.ph.domain.util.Result
import com.alexc.ph.onealexapp.ui.movies.MoviesUiState.Error
import com.alexc.ph.onealexapp.ui.movies.MoviesUiState.Loading
import com.alexc.ph.onealexapp.ui.movies.MoviesUiState.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class MoviesViewModel(
    getMovieAndTvSeriesUseCase: GetMovieAndTvSeriesUseCase,
): ViewModel() {

    val moviesUiState: StateFlow<MoviesUiState> = moviesAndTvSeriesUiState(
        getMovieAndTvSeriesUseCase = getMovieAndTvSeriesUseCase
    )
    .catch { Error(it) }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Loading)

    fun retry() {

    }
}

private fun moviesAndTvSeriesUiState(
    page: Int = 1,
    getMovieAndTvSeriesUseCase: GetMovieAndTvSeriesUseCase,
): Flow<MoviesUiState> {
    return getMovieAndTvSeriesUseCase("en-US", page)
        .map { result ->
            when(result) {
                is Result.Loading -> Loading
                is Result.Success -> Success(content = result.data)
                is Result.Error -> Error(result.exception)
            }
        }
}


sealed interface MoviesUiState {
    object Loading : MoviesUiState
    data class Error(val throwable: Throwable) : MoviesUiState
    data class Success(
        val content: CombinedMoviesAndSeries
    ) : MoviesUiState
}
