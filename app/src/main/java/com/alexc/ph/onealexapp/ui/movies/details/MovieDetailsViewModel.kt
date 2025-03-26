package com.alexc.ph.onealexapp.ui.movies.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alexc.ph.domain.GetMovieDetailsUseCase
import com.alexc.ph.domain.model.ContentItem
import com.alexc.ph.domain.model.ContentType
import com.alexc.ph.domain.model.Result
import com.alexc.ph.onealexapp.ui.movies.details.MovieDetailsUiState.Error
import com.alexc.ph.onealexapp.ui.movies.details.MovieDetailsUiState.Loading
import com.alexc.ph.onealexapp.ui.movies.details.MovieDetailsUiState.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MovieDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    getMovieDetailsUseCase: GetMovieDetailsUseCase
): ViewModel() {
    private val id = savedStateHandle.toRoute<MovieDetailsRoute>().id
    private val contentType = savedStateHandle.toRoute<MovieDetailsRoute>().contentType

    val moviesUiState: StateFlow<MovieDetailsUiState> = movieDetailsUiState(
        id,
        contentType,
        getMovieDetailsUseCase
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Loading)
}

private fun movieDetailsUiState(
    movieId: Int,
    contentType: ContentType,
    getMovieDetailsUseCase: GetMovieDetailsUseCase,
): Flow<MovieDetailsUiState> {
    return getMovieDetailsUseCase(movieId, contentType)
        .map { result ->
            when(result) {
                is Result.Loading -> Loading
                is Result.Success -> Success(contentItem = result.data)
                is Result.Error -> Error(result.exception)
            }
        }
}

sealed interface MovieDetailsUiState {
    object Loading : MovieDetailsUiState
    data class Error(val throwable: Throwable) : MovieDetailsUiState
    data class Success(
        val contentItem: ContentItem,
    ) : MovieDetailsUiState
}