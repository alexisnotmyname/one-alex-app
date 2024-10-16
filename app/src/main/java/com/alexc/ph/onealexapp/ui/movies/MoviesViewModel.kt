package com.alexc.ph.onealexapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexc.ph.data.network.util.Result
import com.alexc.ph.domain.GetMovieAndTvSeriesUseCase
import com.alexc.ph.domain.GetPopularMoviesUseCase
import com.alexc.ph.domain.model.CombinedMoviesAndSeries
import com.alexc.ph.domain.model.Movie
import com.alexc.ph.onealexapp.ui.movies.MoviesUiState.Error
import com.alexc.ph.onealexapp.ui.movies.MoviesUiState.Loading
import com.alexc.ph.onealexapp.ui.movies.MoviesUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    getMovieAndTvSeriesUseCase: GetMovieAndTvSeriesUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase
): ViewModel() {

    private val _moviesPaged: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(value = PagingData.empty())
    val moviesPaged: StateFlow<PagingData<Movie>> get() = _moviesPaged

    val moviesUiState: StateFlow<MoviesUiState> = moviesAndTvSeriesUiState(
        getMovieAndTvSeriesUseCase = getMovieAndTvSeriesUseCase
    )
    .catch { Error(it) }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Loading)

//    init {
//        viewModelScope.launch {
//            loadPaginatedMovies()
//        }
//    }

    fun loadPaginatedMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _moviesPaged.value = pagingData
                }
        }
    }

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
