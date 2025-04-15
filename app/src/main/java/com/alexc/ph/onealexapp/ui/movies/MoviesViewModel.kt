package com.alexc.ph.onealexapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexc.ph.domain.GetMovieAndTvSeriesUseCase
import com.alexc.ph.domain.model.AllMovies
import com.alexc.ph.domain.model.AllTvSeries
import com.alexc.ph.domain.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


class MoviesViewModel(
    private val getMovieAndTvSeriesUseCase: GetMovieAndTvSeriesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesState())
    val state = _state
        .onStart {
            getMoviesAndTvSeries()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    fun onAction(action: MoviesAction) {
        when (action) {
            MoviesAction.OnRetryClicked -> getMoviesAndTvSeries()
        }
    }

    private fun getMoviesAndTvSeries() {
        _state.update { it.copy(isLoading = true) }
        getMovieAndTvSeriesUseCase("en-US", 1)
            .map { result ->
                _state.update {
                    when (result) {
                        is Result.Error -> it.copy(
                            error = result.exception.message ?: "Unknown error",
                            isLoading = false,
                            movies = AllMovies(popular = emptyList(), nowPlaying = emptyList()),
                            tvSeries = AllTvSeries(popular = emptyList(), topRated = emptyList())
                        )
                        is Result.Success -> it.copy(
                            error = "",
                            isLoading = false,
                            movies = result.data.movies,
                            tvSeries = result.data.tvSeries,
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

}



