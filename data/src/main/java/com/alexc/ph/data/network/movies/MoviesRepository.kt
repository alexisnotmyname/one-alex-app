package com.alexc.ph.data.network.movies

import com.alexc.ph.data.model.movies.Movie
import com.alexc.ph.data.util.OAResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MoviesRepository {
    suspend fun getNowPlaying(language: String, page: Int): Flow<OAResults<List<Movie>>>
    suspend fun getPopular(language: String, page: Int): Flow<OAResults<List<Movie>>>
}

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRetrofit: MoviesRetrofit
) : MoviesRepository{
    override suspend fun getNowPlaying(language: String, page: Int): Flow<OAResults<List<Movie>>> = flow {
        emit(OAResults.Loading)
        try{
            val response = moviesRetrofit.getNowPlaying(language, page)
            if(response.isSuccessful) {
                response.body()?.let {
                    emit(OAResults.Success(it.movies))
                } ?: emit(OAResults.Error(Throwable("Response body is null")))
            } else {
                emit(OAResults.Error(Throwable("Error ${response.code()}: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(OAResults.Error(e))
        }
    }

    override suspend fun getPopular(language: String, page: Int): Flow<OAResults<List<Movie>>>  = flow {

    }

}