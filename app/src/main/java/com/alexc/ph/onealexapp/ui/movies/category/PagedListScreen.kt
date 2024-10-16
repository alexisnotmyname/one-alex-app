package com.alexc.ph.onealexapp.ui.movies.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alexc.ph.domain.model.BaseContent
import com.alexc.ph.domain.model.Category
import com.alexc.ph.domain.model.Movie
import com.alexc.ph.onealexapp.ui.components.GenericErrorScreen
import com.alexc.ph.onealexapp.ui.components.GenericTopAppBar
import com.alexc.ph.onealexapp.ui.components.LoadingScreen
import com.alexc.ph.onealexapp.ui.constants.MOVIE_IMAGE_SIZE_DP
import com.alexc.ph.onealexapp.ui.constants.MediumDp
import com.alexc.ph.onealexapp.ui.constants.TOP_APP_BAR_HEIGHT_DP
import com.alexc.ph.onealexapp.ui.movies.MovieItem
import com.alexc.ph.onealexapp.ui.movies.details.MovieDetailsUiState
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun PagedListScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateToMovieDetails: (BaseContent) -> Unit,
    viewModel: PagedListViewModel = hiltViewModel()
) {
    val pagedListUiState by viewModel.pagedListUiState.collectAsStateWithLifecycle()
    val content = viewModel.moviesPaged.collectAsLazyPagingItems()

    when(pagedListUiState) {
        is PagedListUiState.Error -> GenericErrorScreen()
        PagedListUiState.Loading -> LoadingScreen()
        is PagedListUiState.Success -> {
            val category = (pagedListUiState as  PagedListUiState.Success).category
            PagedListContent(
                title = category.value,
                content = content,
                navigateBack = navigateBack,
                navigateToMovieDetails = navigateToMovieDetails,
                modifier = modifier
            )
        }
    }
}

@Composable
fun PagedListContent(
    modifier: Modifier = Modifier,
    title: String,
    content: LazyPagingItems<BaseContent>,
    navigateBack: () -> Unit,
    navigateToMovieDetails: (BaseContent) -> Unit
) {
    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = {
            GenericTopAppBar(
                title = title,
                navigateBack = navigateBack,
                modifier = Modifier
                    .height(TOP_APP_BAR_HEIGHT_DP)
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        MoviesGrid(
            movies = content,
            navigateToMovieDetails = navigateToMovieDetails,
            modifier = Modifier.padding(contentPadding),
        )
    }
}


@Composable
fun MoviesGrid(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<BaseContent>,
    navigateToMovieDetails: (BaseContent) -> Unit = {}
) {
    LazyVerticalGrid (
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = MediumDp),
        horizontalArrangement = Arrangement.spacedBy(MediumDp),
        verticalArrangement = Arrangement.spacedBy(MediumDp),
        modifier = modifier
    ) {
        movies(
            movies = movies,
            navigateToMovieDetails = navigateToMovieDetails
        )
    }
}

fun LazyGridScope.movies(
    movies: LazyPagingItems<BaseContent>,
    navigateToMovieDetails: (BaseContent) -> Unit = {}
) {
    items(movies.itemCount) { index ->
        val movie = movies[index] ?: return@items
        MovieItem(
            modifier = Modifier
                .width(MOVIE_IMAGE_SIZE_DP)
                .aspectRatio(2 / 3f)
                .clickable {
                    navigateToMovieDetails(movie)
                },
            imageUrl = movie.posterPath,
            title = movie.title,
        )
    }
}