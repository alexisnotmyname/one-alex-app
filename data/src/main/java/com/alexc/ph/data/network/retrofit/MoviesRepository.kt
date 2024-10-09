package com.alexc.ph.data.network.retrofit

import com.alexc.ph.data.network.model.Configuration
import com.alexc.ph.data.network.model.MoviesResponse
import com.alexc.ph.data.network.util.Result
import com.alexc.ph.data.network.util.result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MoviesRepository {
    fun getNowPlaying(language: String, page: Int): Flow<Result<MoviesResponse>>
    fun getPopular(language: String, page: Int): Flow<Result<MoviesResponse>>
    fun getImageConfigurations(): Flow<Result<Configuration>>
}

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRetrofit: MoviesRetrofit
) : MoviesRepository{
    override fun getNowPlaying(language: String, page: Int): Flow<Result<MoviesResponse>> = flow {
        emit(Result.Loading)
        val result = result { moviesRetrofit.getNowPlaying(language, page) }
        emit(result)
    }

    override fun getPopular(language: String, page: Int): Flow<Result<MoviesResponse>>  = flow {
        emit(Result.Loading)
        val result = result { moviesRetrofit.getPopular(language, page) }
        emit(result)
    }

    override fun getImageConfigurations(): Flow<Result<Configuration>> = flow {
        emit(Result.Loading)
        val result = result { moviesRetrofit.getConfiguration() }
        emit(result)
    }
}
