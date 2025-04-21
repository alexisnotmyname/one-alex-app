package com.alexc.ph.onealexapp.ui.movies.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alexc.ph.domain.GetMovieDetailsUseCase
import com.alexc.ph.domain.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MovieDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
): ViewModel() {
    private val id = savedStateHandle.toRoute<MovieDetailsRoute>().id
    private val contentType = savedStateHandle.toRoute<MovieDetailsRoute>().contentType


    private val _state = MutableStateFlow(MovieDetailsState())
    val state = _state
        .onStart {
            getContentDetails()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    fun onAction(action: MovieDetailsAction) {
        when (action) {
            MovieDetailsAction.OnRetryClicked -> getContentDetails()
        }
    }

    private fun getContentDetails() {
        _state.update { it.copy(isLoading = true) }
        getMovieDetailsUseCase(id, contentType)
            .map { result ->
                _state.update {
                    when(result) {
                        is Result.Error -> it.copy(
                            error = result.exception.message ?: "Unknown error",
                            isLoading = false
                        )
                        is Result.Success -> it.copy(
                            error = "",
                            isLoading = false,
                            contentItem = result.data
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}

