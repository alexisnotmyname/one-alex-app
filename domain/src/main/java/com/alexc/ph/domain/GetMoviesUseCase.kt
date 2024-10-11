package com.alexc.ph.domain

import com.alexc.ph.data.network.retrofit.MoviesRepository
import com.alexc.ph.data.network.util.Result
import com.alexc.ph.domain.model.CombinedMovies
import com.alexc.ph.domain.model.Movies
import com.alexc.ph.domain.model.toMoviesDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(language: String, page: Int): Flow<Result<CombinedMovies>> = combine(
        moviesRepository.getNowPlaying(language, page),
        moviesRepository.getPopular(language, page)
    ) { nowPlaying, popular ->

        if(nowPlaying is Result.Success && popular is Result.Success) {
            Result.Success(
                CombinedMovies(
                    nowPlaying = nowPlaying.data.toMoviesDomain().movies,
                    popular = popular.data.toMoviesDomain().movies
                )
            )
        } else {
            val errorMessage = buildString {
                if (nowPlaying is Result.Error) append("Error in getNowPlaying: ${nowPlaying.exception.message}")
                if (popular is Result.Error) append("Error in getPopular: ${popular.exception.message}")
            }
            Result.Error(Exception(errorMessage))
        }
    }
}