package com.alexc.ph.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alexc.ph.data.network.datasource.MovieDataSource
import com.alexc.ph.data.network.model.toTvSeries
import com.alexc.ph.data.repository.paging.PopularTvSeriesPagingSource
import com.alexc.ph.data.repository.paging.TopRatedTvSeriesPagingSource
import com.alexc.ph.domain.util.Result
import com.alexc.ph.domain.model.TvSeries
import com.alexc.ph.domain.repository.TvSeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvSeriesRepositoryImpl(
    private val moviesDataSource: MovieDataSource
): TvSeriesRepository {
    override fun getTvSeries(id: Int): Flow<Result<TvSeries>> = flow {
        try {
            emit(Result.Success(moviesDataSource.getTvSeries(id).toTvSeries()))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getTopRated(language: String, page: Int): Flow<Result<List<TvSeries>>> = flow {
        try {
            emit(Result.Success(moviesDataSource.getTopRatedTvSeries(page).results.map { it.toTvSeries() }))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getPopular(language: String, page: Int): Flow<Result<List<TvSeries>>> = flow {
        try {
            emit(Result.Success(moviesDataSource.getPopularTvSeries(page).results.map { it.toTvSeries() }))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getTopRatedPaged(): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { TopRatedTvSeriesPagingSource(moviesDataSource) }
        ).flow
    }

    override fun getPopularPaged(): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PopularTvSeriesPagingSource(moviesDataSource) }
        ).flow
    }
}
