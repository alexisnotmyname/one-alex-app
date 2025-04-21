package com.alexc.ph.data.repository

import com.alexc.ph.data.network.datasource.MovieDataSource
import com.alexc.ph.data.network.model.toSearch
import com.alexc.ph.domain.model.Search
import com.alexc.ph.domain.repository.SearchRepository
import com.alexc.ph.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(
    private val movieDataSource: MovieDataSource,
): SearchRepository {
    override fun search(query: String, page: Int): Flow<Result<List<Search>>> = flow {
        try {
            emit(Result.Success(movieDataSource.search(query, page).results.map { it.toSearch() }))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}