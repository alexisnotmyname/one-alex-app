package com.alexc.ph.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alexc.ph.data.network.datasource.MovieDataSource
import com.alexc.ph.data.network.model.MovieResponse
import com.alexc.ph.data.network.model.TvSeriesResponse
import com.alexc.ph.data.network.util.Result
import com.alexc.ph.data.repository.paging.NowPlayingMoviesPagingSource
import com.alexc.ph.data.repository.paging.PopularMoviesPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MoviesRepository {
    fun getMovie(id: Int): Flow<Result<MovieResponse>>
    fun getNowPlaying(): Flow<Result<List<MovieResponse>>>
    fun getPopular(): Flow<Result<List<MovieResponse>>>
    fun getNowPlayingPaged(): Flow<PagingData<MovieResponse>>
    fun getPopularPaged(): Flow<PagingData<MovieResponse>>
}

class MoviesRepositoryImpl @Inject constructor(
    private val movieDataSource: MovieDataSource
) : MoviesRepository {

    override fun getMovie(id: Int): Flow<Result<MovieResponse>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(movieDataSource.getMovie(id)))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getNowPlaying(): Flow<Result<List<MovieResponse>>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(movieDataSource.getNowPlayingMovies(1).results))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getPopular(): Flow<Result<List<MovieResponse>>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(movieDataSource.getPopularMovies(1).results))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getNowPlayingPaged(): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { NowPlayingMoviesPagingSource(movieDataSource) }
        ).flow
    }

    override fun getPopularPaged(): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PopularMoviesPagingSource(movieDataSource) }
        ).flow
    }
}
