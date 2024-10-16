package com.alexc.ph.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alexc.ph.data.network.datasource.MovieDataSource
import com.alexc.ph.data.network.model.TvSeriesResponse
import com.alexc.ph.data.network.util.Result
import com.alexc.ph.data.repository.paging.PopularTvSeriesPagingSource
import com.alexc.ph.data.repository.paging.TopRatedTvSeriesPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface TvSeriesRepository {
    fun getTvSeries(id: Int): Flow<Result<TvSeriesResponse>>
    fun getTopRated(language: String, page: Int): Flow<Result<List<TvSeriesResponse>>>
    fun getPopular(language: String, page: Int): Flow<Result<List<TvSeriesResponse>>>
    fun getTopRatedPaged(): Flow<PagingData<TvSeriesResponse>>
    fun getPopularPaged(): Flow<PagingData<TvSeriesResponse>>
}

class TvSeriesRepositoryImpl @Inject constructor(
    private val moviesDataSource: MovieDataSource
): TvSeriesRepository {
    override fun getTvSeries(id: Int): Flow<Result<TvSeriesResponse>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(moviesDataSource.getTvSeries(id)))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getTopRated(language: String, page: Int): Flow<Result<List<TvSeriesResponse>>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(moviesDataSource.getTopRatedTvSeries(page).results))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getPopular(language: String, page: Int): Flow<Result<List<TvSeriesResponse>>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(moviesDataSource.getPopularTvSeries(page).results))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getTopRatedPaged(): Flow<PagingData<TvSeriesResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { TopRatedTvSeriesPagingSource(moviesDataSource) }
        ).flow
    }

    override fun getPopularPaged(): Flow<PagingData<TvSeriesResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PopularTvSeriesPagingSource(moviesDataSource) }
        ).flow
    }
}
