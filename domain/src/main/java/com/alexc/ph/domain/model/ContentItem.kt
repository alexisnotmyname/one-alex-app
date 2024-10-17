package com.alexc.ph.domain.model

sealed class ContentItem {
    data class MovieItem(val movie: Movie) : ContentItem()
    data class TvSeriesItem(val tvSeries: TvSeries) : ContentItem()
}