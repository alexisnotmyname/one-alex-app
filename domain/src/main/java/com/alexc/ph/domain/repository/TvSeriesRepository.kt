package com.alexc.ph.domain.repository

import androidx.paging.PagingData
import com.alexc.ph.domain.model.Result
import com.alexc.ph.domain.model.TvSeries
import kotlinx.coroutines.flow.Flow

interface TvSeriesRepository {
    fun getTvSeries(id: Int): Flow<Result<TvSeries>>
    fun getTopRated(language: String, page: Int): Flow<Result<List<TvSeries>>>
    fun getPopular(language: String, page: Int): Flow<Result<List<TvSeries>>>
    fun getTopRatedPaged(): Flow<PagingData<TvSeries>>
    fun getPopularPaged(): Flow<PagingData<TvSeries>>
}