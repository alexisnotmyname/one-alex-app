package com.alexc.ph.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.alexc.ph.data.repository.MoviesRepository
import com.alexc.ph.data.repository.TvSeriesRepository
import com.alexc.ph.domain.model.BaseContent
import com.alexc.ph.domain.model.Category
import com.alexc.ph.domain.model.toMovie
import com.alexc.ph.domain.model.toTvSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPagedContentUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvSeriesRepository: TvSeriesRepository
) {
    operator fun invoke(category: Category): Flow<PagingData<BaseContent>> {
        return when(category) {
            Category.NOW_PLAYING_MOVIES -> {
                moviesRepository.getNowPlayingPaged().map { movies ->
                    movies.map { it.toMovie() }
                }
            }
            Category.POPULAR_MOVIES -> {
                moviesRepository.getPopularPaged().map { movies ->
                    movies.map { it.toMovie() }
                }
            }
            Category.POPULAR_TV_SERIES -> {
                tvSeriesRepository.getPopularPaged().map { tv ->
                    tv.map { it.toTvSeries() }
                }
            }
            Category.TOP_RATED_TV_SERIES -> {
                tvSeriesRepository.getTopRatedPaged().map { tv ->
                    tv.map { it.toTvSeries() }
                }
            }
        }
    }
}