package com.alexc.ph.data.network.movies

import com.alexc.ph.data.model.movies.Configuration
import com.alexc.ph.data.model.movies.Movie
import com.alexc.ph.data.model.movies.Movies
import com.alexc.ph.data.util.Result
import com.alexc.ph.data.util.result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MoviesRepository {
    fun getNowPlaying(language: String, page: Int): Flow<Result<Movies>>
    fun getPopular(language: String, page: Int): Flow<Result<Movies>>
    fun getImageConfigurations(): Flow<Result<Configuration>>
}

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRetrofit: MoviesRetrofit
) : MoviesRepository{
    override fun getNowPlaying(language: String, page: Int): Flow<Result<Movies>> = flow {
        emit(Result.Loading)
        val result = result { moviesRetrofit.getNowPlaying(language, page) }
        emit(result)
    }

    override fun getPopular(language: String, page: Int): Flow<Result<Movies>>  = flow {
        emit(Result.Loading)
        val result = result{ moviesRetrofit.getPopular(language, page) }
        emit(result)
    }

    override fun getImageConfigurations(): Flow<Result<Configuration>> = flow {
        emit(Result.Loading)
        val result = result { moviesRetrofit.getConfiguration() }
        emit(result)
    }
}
