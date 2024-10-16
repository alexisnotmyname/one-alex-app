package com.alexc.ph.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alexc.ph.data.network.datasource.MovieDataSource
import com.alexc.ph.data.network.model.MovieResponse
import retrofit2.HttpException
import java.io.IOException

class PopularMoviesPagingSource(
    private val movieDataSource: MovieDataSource
) : PagingSource<Int, MovieResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        return try {
            val currentPage = params.key ?: 1
            val movies = movieDataSource.getPopularMovies(
                page = currentPage
            )

            LoadResult.Page(
                data = movies.results,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.results.isEmpty()) null else movies.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition
    }
}