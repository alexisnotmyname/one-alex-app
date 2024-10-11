package com.alexc.ph.domain

import com.alexc.ph.data.network.retrofit.MoviesRepository
import com.alexc.ph.data.network.util.Result
import com.alexc.ph.domain.model.Movies
import com.alexc.ph.domain.model.toMoviesDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(language: String, page: Int): Flow<Result<Movies>> {
        return moviesRepository.getNowPlaying(language, page).map { result ->
            when(result) {
                is Result.Loading -> Result.Loading
                is Result.Success -> Result.Success(result.data.toMoviesDomain())
                is Result.Error -> Result.Error(result.exception)
            }
        }
    }
}