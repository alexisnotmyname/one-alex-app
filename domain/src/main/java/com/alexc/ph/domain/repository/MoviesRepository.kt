package com.alexc.ph.domain.repository

import androidx.paging.PagingData
import com.alexc.ph.domain.model.Movie
import com.alexc.ph.domain.util.Result
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    fun getMovie(id: Int): Flow<Result<Movie>>
    fun getNowPlaying(): Flow<Result<List<Movie>>>
    fun getPopular(): Flow<Result<List<Movie>>>
    fun getNowPlayingPaged(): Flow<PagingData<Movie>>
    fun getPopularPaged(): Flow<PagingData<Movie>>
}