package com.alexc.ph.data.network.retrofit

import com.alexc.ph.data.network.model.Configuration
import com.alexc.ph.data.network.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesRetrofit {

    @GET("3/movie/now_playing")
    suspend fun getNowPlaying(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET("3/movie/popular")
    suspend fun getPopular(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET("3/configuration")
    suspend fun getConfiguration(): Response<Configuration>
}