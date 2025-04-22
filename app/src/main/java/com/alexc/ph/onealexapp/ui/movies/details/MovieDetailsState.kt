package com.alexc.ph.onealexapp.ui.movies.details

import com.alexc.ph.domain.model.ContentItem

data class MovieDetailsState(
    val contentItem: ContentItem? = null,
    val isLoading: Boolean = true,
    val error: String = ""
)