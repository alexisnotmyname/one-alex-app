package com.alexc.ph.domain

import com.alexc.ph.domain.model.AllMovies
import com.alexc.ph.domain.model.CombinedMoviesAndTvSeries
import com.alexc.ph.domain.model.AllTvSeries
import com.alexc.ph.domain.util.Result
import com.alexc.ph.domain.repository.MoviesRepository
import com.alexc.ph.domain.repository.TvSeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine

class GetMovieAndTvSeriesUseCase(
    private val moviesRepository: MoviesRepository,
    private val tvSeriesRepository: TvSeriesRepository
) {
    operator fun invoke(language: String, page: Int): Flow<Result<CombinedMoviesAndTvSeries>> {
        return combine(
            moviesRepository.getNowPlaying(),
            moviesRepository.getPopular(),
            tvSeriesRepository.getTopRated(language, page),
            tvSeriesRepository.getPopular(language, page)
        ) { nowPlayingMovies, popularMovies, topRatedTvSeries, popularTvSeries ->

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
                CombinedMoviesAndTvSeries(
                    movies = AllMovies(nowPlaying = moviesNowPlaying, popular = moviesPopular),
                    tvSeries = AllTvSeries(topRated = tvSeriesTopRated, popular = tvSeriesPopular)
                )
            )
        }
        .catch { e -> emit(Result.Error(e)) }
    }
}