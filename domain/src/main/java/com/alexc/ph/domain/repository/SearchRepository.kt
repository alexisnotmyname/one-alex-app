package com.alexc.ph.domain.repository

import com.alexc.ph.domain.model.Search
import com.alexc.ph.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: String, page: Int): Flow<Result<List<Search>>>
}