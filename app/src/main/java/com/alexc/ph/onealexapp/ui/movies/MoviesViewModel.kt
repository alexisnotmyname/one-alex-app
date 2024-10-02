package com.alexc.ph.onealexapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexc.ph.data.model.movies.Movie
import com.alexc.ph.data.network.movies.MoviesRepository
import com.alexc.ph.data.util.OAResults
import com.alexc.ph.onealexapp.ui.movies.MoviesUiState.Error
import com.alexc.ph.onealexapp.ui.movies.MoviesUiState.Success
import com.alexc.ph.onealexapp.ui.todolist.TodoListUiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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

    private var _movies = MutableStateFlow<List<Movie>>(emptyList())

    val uiState = _movies
        .onStart { getNowPlaying("en-US", 1) }
        .map<List<Movie>, MoviesUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Loading)

    private fun getNowPlaying(language: String, page: Int) {
        viewModelScope.launch {
            val moviesFlow = moviesRepository.getNowPlaying(language, page)
            moviesFlow.collect { response ->
                if(response is OAResults.Success) {
                    _movies.value = response.data
                }
            }
        }
    }
}

sealed interface MoviesUiState {
    object Loading : MoviesUiState
    data class Error(val throwable: Throwable) : MoviesUiState
    data class Success(val movies: List<Movie>) : MoviesUiState
}