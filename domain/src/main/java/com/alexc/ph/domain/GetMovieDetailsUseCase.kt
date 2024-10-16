package com.alexc.ph.domain

import com.alexc.ph.data.repository.MoviesRepository
import com.alexc.ph.data.network.util.Result
import com.alexc.ph.data.repository.TvSeriesRepository
import com.alexc.ph.domain.model.ContentItem
import com.alexc.ph.domain.model.ContentType
import com.alexc.ph.domain.model.toMovie
import com.alexc.ph.domain.model.toTvSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvSeriesRepository: TvSeriesRepository
) {
    operator fun invoke(id: Int, contentType: ContentType): Flow<Result<ContentItem>> {
        return when(contentType) {
            ContentType.MOVIE -> {
                moviesRepository.getMovie(id).map { result ->
                    when(result) {
                        is Result.Error -> Result.Error(result.exception)
                        Result.Loading -> Result.Loading
                        is Result.Success -> Result.Success(ContentItem.MovieItem(result.data.toMovie()))
                    }
                }
            }
            ContentType.TV -> {
                tvSeriesRepository.getTvSeries(id).map { result ->
                    when(result) {
                        is Result.Error -> Result.Error(result.exception)
                        Result.Loading -> Result.Loading
                        is Result.Success -> Result.Success(ContentItem.TvSeriesItem(result.data.toTvSeries()))
                    }
                }
            }
        }
    }
}