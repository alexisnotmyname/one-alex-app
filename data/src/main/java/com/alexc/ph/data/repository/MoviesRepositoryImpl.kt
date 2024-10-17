package com.alexc.ph.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alexc.ph.data.network.datasource.MovieDataSource
import com.alexc.ph.data.network.model.toMovie
import com.alexc.ph.domain.model.Result
import com.alexc.ph.data.repository.paging.NowPlayingMoviesPagingSource
import com.alexc.ph.data.repository.paging.PopularMoviesPagingSource
import com.alexc.ph.domain.model.Movie
import com.alexc.ph.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieDataSource: MovieDataSource
) : MoviesRepository {

    override fun getMovie(id: Int): Flow<Result<Movie>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(movieDataSource.getMovie(id).toMovie()))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getNowPlaying(): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(movieDataSource.getNowPlayingMovies(1).results.map { it.toMovie() }))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getPopular(): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(movieDataSource.getPopularMovies(1).results.map { it.toMovie() }))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getNowPlayingPaged(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { NowPlayingMoviesPagingSource(movieDataSource) }
        ).flow
    }

    override fun getPopularPaged(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PopularMoviesPagingSource(movieDataSource) }
        ).flow
    }
}
