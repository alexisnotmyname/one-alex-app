@file:OptIn(FlowPreview::class)

package com.alexc.ph.onealexapp.ui.movies.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexc.ph.domain.SearchUseCase
import com.alexc.ph.domain.util.Result
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    val searchUseCase: SearchUseCase
): ViewModel() {

    private var searchJob: Job? = null

    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .onStart {
            observeSearchQuery()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(query = query) }
    }

    private fun observeSearchQuery() {
        state
            .map { it.query }
            .distinctUntilChanged()
            .debounce(300L)
            .onEach { query ->
                when{
                    query.isBlank() -> {
                        _state.update { it.copy(
                            query = "",
                            searchResults = emptyList(),
                            isLoading = false
                        ) }
                    }
                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = search(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun search(query: String) = viewModelScope.launch {
        searchUseCase(query, 1)
            .onEach { result ->
                _state.update {
                    when(result) {
                        is Result.Error -> it.copy(
                            error = result.exception.message ?: "Unknown error",
                            isLoading = false
                        )
                        is Result.Success -> it.copy(
                            isLoading = false,
                            error = "",
                            searchResults = result.data)
                    }
                }

            }
            .launchIn(viewModelScope)
    }
}