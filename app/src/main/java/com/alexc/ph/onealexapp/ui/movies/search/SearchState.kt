package com.alexc.ph.onealexapp.ui.movies.search

import com.alexc.ph.domain.model.Search

data class SearchState(
    val query: String = "",
    val searchResults: List<Search> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)