package com.alexc.ph.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alexc.ph.data.network.datasource.MovieDataSource
import com.alexc.ph.data.network.model.MovieResponse
import com.alexc.ph.data.network.model.TvSeriesResponse
import retrofit2.HttpException
import java.io.IOException

class PopularTvSeriesPagingSource(
    private val movieDataSource: MovieDataSource
) : PagingSource<Int, TvSeriesResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeriesResponse> {
        return try {
            val currentPage = params.key ?: 1
            val tvSeries = movieDataSource.getPopularTvSeries(
                page = currentPage
            )

            LoadResult.Page(
                data = tvSeries.results,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (tvSeries.results.isEmpty()) null else tvSeries.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvSeriesResponse>): Int? {
        return state.anchorPosition
    }
}