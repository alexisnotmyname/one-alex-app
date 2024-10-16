package com.alexc.ph.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.alexc.ph.data.repository.MoviesRepository
import com.alexc.ph.domain.model.Movie
import com.alexc.ph.domain.model.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return moviesRepository.getPopularPaged().map { movies ->
            movies.map { it.toMovie() }
        }
    }
}