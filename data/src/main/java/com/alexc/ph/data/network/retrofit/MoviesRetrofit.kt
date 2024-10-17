package com.alexc.ph.data.network.retrofit

import com.alexc.ph.data.network.model.ConfigurationResponse
import com.alexc.ph.data.network.model.MovieResponse
import com.alexc.ph.data.network.model.ResponseDto
import com.alexc.ph.data.network.model.TvSeriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesRetrofit {

    @GET("3/movie/{id}")
    suspend fun getMovie(
        @Path(value = "id") id: Int,
        @Query("language") language: String = "en-US"
    ): MovieResponse

    @GET("3/tv/{id}")
    suspend fun getTvSeries(
        @Path(value = "id") id: Int,
        @Query("language") language: String = "en-US"
    ): TvSeriesResponse

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): ResponseDto<List<MovieResponse>>

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): ResponseDto<List<MovieResponse>>

    @GET("3/tv/top_rated")
    suspend fun getTopRatedTvSeries(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): ResponseDto<List<TvSeriesResponse>>

    @GET("3/tv/popular")
    suspend fun getPopularTvSeries(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): ResponseDto<List<TvSeriesResponse>>

    @GET("3/configuration")
    suspend fun getConfiguration(): Response<ConfigurationResponse>
}