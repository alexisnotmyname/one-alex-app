package com.alexc.ph.data.network.datasource

import com.alexc.ph.data.network.model.MovieResponse
import com.alexc.ph.data.network.model.ResponseDto
import com.alexc.ph.data.network.model.TvSeriesResponse

interface MovieDataSource {
    suspend fun getMovie(id: Int): MovieResponse
    suspend fun getTvSeries(id: Int): TvSeriesResponse
    suspend fun getNowPlayingMovies(page: Int): ResponseDto<List<MovieResponse>>
    suspend fun getPopularMovies(page: Int): ResponseDto<List<MovieResponse>>
    suspend fun getTopRatedTvSeries(page: Int): ResponseDto<List<TvSeriesResponse>>
    suspend fun getPopularTvSeries(page: Int): ResponseDto<List<TvSeriesResponse>>
}