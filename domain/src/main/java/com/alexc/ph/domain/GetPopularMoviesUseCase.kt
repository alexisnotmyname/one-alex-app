package com.alexc.ph.domain

import com.alexc.ph.data.network.model.MoviesResponse
import com.alexc.ph.data.network.retrofit.MoviesRepository
import com.alexc.ph.data.network.util.Result
import com.alexc.ph.domain.model.Movies
import com.alexc.ph.domain.model.Movies.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(language: String, page: Int): Flow<Result<Movies>> {
        return moviesRepository.getPopular(language, page).map { result ->
            when(result) {
                is Result.Loading -> Result.Loading
                is Result.Success -> Result.Success(result.data.toMoviesDomain())
                is Result.Error -> Result.Error(result.exception)
            }
        }
    }
}

fun MoviesResponse.toMoviesDomain() = Movies(
    movies = this.movies.map { movie ->
        Movie(
            id = movie.id,
            title = movie.title,
            video = movie.video,
            adult = movie.adult,
            overview = movie.overview,
            popularity = movie.popularity,
            genreIds = movie.genreIds,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            backDropPath = movie.backDropPath,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount
        )
    },
    page = this.page,
    totalPages = this.totalPages,
    totalResults = this.totalResults
)