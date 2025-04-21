package com.alexc.ph.domain

import com.alexc.ph.domain.model.Search
import com.alexc.ph.domain.repository.SearchRepository
import com.alexc.ph.domain.util.Result
import kotlinx.coroutines.flow.Flow

class SearchUseCase(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(query: String, page: Int): Flow<Result<List<Search>>> = searchRepository.search(query, page)
}