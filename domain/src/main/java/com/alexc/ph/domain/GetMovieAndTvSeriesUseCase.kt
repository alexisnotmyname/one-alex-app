package com.alexc.ph.domain

import com.alexc.ph.domain.model.CombinedMovies
import com.alexc.ph.domain.model.CombinedMoviesAndSeries
import com.alexc.ph.domain.model.CombinedTvSeries
import com.alexc.ph.domain.util.Result
import com.alexc.ph.domain.repository.MoviesRepository
import com.alexc.ph.domain.repository.TvSeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart

class GetMovieAndTvSeriesUseCase(
    private val moviesRepository: MoviesRepository,
    private val tvSeriesRepository: TvSeriesRepository
) {
    operator fun invoke(language: String, page: Int): Flow<Result<CombinedMoviesAndSeries>> {
        return combine(
            moviesRepository.getNowPlaying(),
            moviesRepository.getPopular(),
            tvSeriesRepository.getTopRated(language, page),
            tvSeriesRepository.getPopular(language, page)
        ) { nowPlayingMovies, popularMovies, topRatedTvSeries, popularTvSeries ->

            val loading = listOf(
                nowPlayingMovies,
                popularMovies,
                topRatedTvSeries,
                popularTvSeries
            )
            .filterIsInstance<Result.Loading>()
            .firstOrNull()

            if (loading != null) {
                return@combine Result.Loading
            }

            val error = listOf(
                nowPlayingMovies,
                popularMovies,
                topRatedTvSeries,
                popularTvSeries
            )
            .filterIsInstance<Result.Error>()
            .firstOrNull()

            if (error != null) {
                return@combine Result.Error(error.exception)
            }

            val moviesNowPlaying = (nowPlayingMovies as Result.Success).data
            val moviesPopular = (popularMovies as Result.Success).data
            val tvSeriesTopRated = (topRatedTvSeries as Result.Success).data
            val tvSeriesPopular = (popularTvSeries as Result.Success).data

            return@combine Result.Success(
                CombinedMoviesAndSeries(
                    movies = CombinedMovies(nowPlaying = moviesNowPlaying, popular = moviesPopular),
                    tvSeries = CombinedTvSeries(topRated = tvSeriesTopRated, popular = tvSeriesPopular)
                )
            )
        }
        .onStart { emit(Result.Loading) }
        .catch { e -> emit(Result.Error(e)) }
    }
}