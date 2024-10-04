package com.alexc.ph.onealexapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexc.ph.data.model.movies.Movie
import com.alexc.ph.data.network.movies.MoviesRepository
import com.alexc.ph.data.util.Result
import com.alexc.ph.onealexapp.ui.movies.MoviesUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {

    private var _configurations = MutableStateFlow<Configuration?>(null)

    val moviesUiState: StateFlow<MoviesUiState> = moviesUiState(
        moviesRepository,
        _configurations.value
    )
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Loading)

    //using onStart to load initial data
//    private var _moviesUiState = MutableStateFlow<MoviesUiState>(Idle)
//    val moviesUiState: StateFlow<MoviesUiState> = _moviesUiState
//        .onStart { getPopular("en-US", 1) }
//        .catch { emit(Error(it)) }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Loading)
//
//    fun getPopular(language: String, page: Int) {
//        viewModelScope.launch {
//            moviesRepository.getPopular(language, page)
//                .map { result ->
//                    when(result) {
//                        is Result.Loading -> Loading
//                        is Result.Success -> {
//                            val baseUrl = _configurations.value?.baseUrl ?: "https://image.tmdb.org/t/p/"
//                            val size = _configurations.value?.imageSize ?: "original"
//                            val data = result.data.movies.map {
//                                it.copy(posterPath = baseUrl+size+it.posterPath)
//                            }
//                            println(data)
//                            Success(data)
//                        }
//                        is Result.Error -> Error(result.exception)
//                    }
//                }.collect { moviesUiState ->
//                    _moviesUiState.value = moviesUiState
//                }
//        }
//    }
}

private fun moviesUiState(
    moviesRepository: MoviesRepository,
    configuration: Configuration?
): Flow<MoviesUiState> {
    return moviesRepository.getPopular("en-US", 1)
        .map { result ->
            when(result) {
                is Result.Loading -> Loading
                is Result.Success -> {
                    val baseUrl = configuration?.baseUrl ?: "https://image.tmdb.org/t/p/"
                    val size = configuration?.imageSize ?: "original"
                    val data = result.data.movies.map {
                        it.copy(posterPath = baseUrl+size+it.posterPath)
                    }
                    Success(data)
                }
                is Result.Error -> Error(result.exception)
            }
        }
}

data class Configuration(val baseUrl: String, val imageSize: String)

sealed interface MoviesUiState {
    object Idle: MoviesUiState
    object Loading : MoviesUiState
    data class Error(val throwable: Throwable) : MoviesUiState
    data class Success(val movies: List<Movie>) : MoviesUiState
}