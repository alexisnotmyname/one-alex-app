package com.alexc.ph.data.network.datasource

import com.alexc.ph.data.network.model.MovieResponse
import com.alexc.ph.data.network.model.ResponseDto
import com.alexc.ph.data.network.model.TvSeriesResponse
import com.alexc.ph.data.network.retrofit.MoviesRetrofit

class MovieDataSourceImpl(
    private val api: MoviesRetrofit
): MovieDataSource {
    override suspend fun getMovie(id: Int): MovieResponse = api.getMovie(id)

    override suspend fun getTvSeries(id: Int): TvSeriesResponse = api.getTvSeries(id)

    override suspend fun getNowPlayingMovies(page: Int): ResponseDto<List<MovieResponse>> = api.getNowPlayingMovies(page = page)

    override suspend fun getPopularMovies(page: Int): ResponseDto<List<MovieResponse>> = api.getPopularMovies(page = page)

    override suspend fun getTopRatedTvSeries(page: Int): ResponseDto<List<TvSeriesResponse>> = api.getTopRatedTvSeries(page = page)

    override suspend fun getPopularTvSeries(page: Int): ResponseDto<List<TvSeriesResponse>> = api.getPopularTvSeries(page = page)
}