package com.alexc.ph.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.alexc.ph.domain.model.BaseContent
import com.alexc.ph.domain.model.Category
import com.alexc.ph.domain.repository.MoviesRepository
import com.alexc.ph.domain.repository.TvSeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPagedContentUseCase(
    private val moviesRepository: MoviesRepository,
    private val tvSeriesRepository: TvSeriesRepository
) {
    operator fun invoke(category: Category): Flow<PagingData<BaseContent>> {
        return when(category) {
            Category.NOW_PLAYING_MOVIES -> {
                moviesRepository.getNowPlayingPaged().map { movies ->
                    movies.map { it }
                }
            }
            Category.POPULAR_MOVIES -> {
                moviesRepository.getPopularPaged().map { movies ->
                    movies.map { it }
                }
            }
            Category.POPULAR_TV_SERIES -> {
                tvSeriesRepository.getPopularPaged().map { tv ->
                    tv.map { it }
                }
            }
            Category.TOP_RATED_TV_SERIES -> {
                tvSeriesRepository.getTopRatedPaged().map { tv ->
                    tv.map { it }
                }
            }
        }
    }
}