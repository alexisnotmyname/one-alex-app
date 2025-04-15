package com.alexc.ph.onealexapp.ui.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexc.ph.domain.model.BaseContent
import com.alexc.ph.domain.model.Category
import com.alexc.ph.domain.model.AllMovies
import com.alexc.ph.domain.model.CombinedMoviesAndTvSeries
import com.alexc.ph.domain.model.AllTvSeries
import com.alexc.ph.domain.model.Movie
import com.alexc.ph.domain.model.TvSeries
import com.alexc.ph.onealexapp.R
import com.alexc.ph.onealexapp.ui.components.ContentImage
import com.alexc.ph.onealexapp.ui.components.GenericErrorScreen
import com.alexc.ph.onealexapp.ui.components.LoadingScreen
import com.alexc.ph.onealexapp.ui.components.OneAlexAppIcons.Forward
import com.alexc.ph.onealexapp.ui.components.OneAlexTopAppBar
import com.alexc.ph.onealexapp.ui.constants.LargeDp
import com.alexc.ph.onealexapp.ui.constants.MOVIE_IMAGE_SIZE_DP
import com.alexc.ph.onealexapp.ui.constants.MediumDp
import com.alexc.ph.onealexapp.ui.constants.SmallDp
import com.alexc.ph.onealexapp.ui.constants.XLargeDp
import com.alexc.ph.onealexapp.ui.theme.shapes
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreenRoot(
    moviesMoviesViewModel: MoviesViewModel = koinViewModel(),
    navigateToMovieDetails: (BaseContent) -> Unit = {},
    navigateToPagedList: (Category) -> Unit = {}
) {
    val state by moviesMoviesViewModel.state.collectAsStateWithLifecycle()
    MoviesScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        navigateToMovieDetails = navigateToMovieDetails,
        onCategoryClicked = navigateToPagedList,
        onRetryClicked = {
                moviesMoviesViewModel.onAction(MoviesAction.OnRetryClicked)
        }
    )
}

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    state: MoviesState,
    navigateToMovieDetails: (BaseContent) -> Unit,
    onCategoryClicked: (Category) -> Unit,
    onRetryClicked: () -> Unit
) {
    Column {
        OneAlexTopAppBar(
            titleRes = R.string.movies,
            searchQuery = "",
            onSearchQueryChange = { query ->

            },
            onImeSearch = {

            }
        )

        if(state.isLoading) {
            LoadingScreen()
        } else {
            when {
                state.error.isNotBlank() -> {
                    GenericErrorScreen(onRetry = onRetryClicked)
                }
                else -> {
                    MovieList(
                        modifier = modifier,
                        state = state,
                        navigateToMovieDetails = navigateToMovieDetails,
                        onCategoryClicked = onCategoryClicked
                    )
                }
            }

        }
    }
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    state: MoviesState,
    navigateToMovieDetails: (BaseContent) -> Unit = {},
    onCategoryClicked: (Category) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
    ) {
        movieItems(
            movies = state.movies.popular,
            navigateToMovieDetails = navigateToMovieDetails
        ) {
            MovieHeaderContent(
                headerTitle = stringResource(R.string.popular_movies),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategoryClicked(Category.POPULAR_MOVIES) }
            )
        }
        movieItems(
            movies = state.movies.nowPlaying,
            navigateToMovieDetails = navigateToMovieDetails
        ) {
            MovieHeaderContent(
                headerTitle = stringResource(R.string.now_playing_movies),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategoryClicked(Category.NOW_PLAYING_MOVIES) }
            )
        }

        movieItems(
            movies = state.tvSeries.popular,
            navigateToMovieDetails = navigateToMovieDetails
        ) {
            MovieHeaderContent(
                headerTitle = stringResource(R.string.popular_series),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategoryClicked(Category.POPULAR_TV_SERIES) }
            )
        }

        movieItems(
            movies = state.tvSeries.topRated,
            navigateToMovieDetails = navigateToMovieDetails
        ) {
            MovieHeaderContent(
                headerTitle = stringResource(R.string.top_rated_series),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategoryClicked(Category.TOP_RATED_TV_SERIES) }
            )
        }

        item {
            Spacer(Modifier.height(LargeDp))
        }
    }
}

fun LazyListScope.movieItems(
    movies: List<BaseContent>,
    navigateToMovieDetails: (BaseContent) -> Unit,
    headerContent: @Composable () -> Unit = {}
) {
    item {
        Spacer(modifier = Modifier.height(MediumDp))
        headerContent()
        MovieRow(
           movies = movies,
           navigateToMovieDetails = navigateToMovieDetails,
           modifier = Modifier.fillParentMaxWidth()
        )
        Spacer(modifier = Modifier.height(MediumDp))
    }
}

@Composable
fun MovieHeaderContent(
    modifier: Modifier = Modifier,
    headerTitle: String
) {
    Row (
        modifier = modifier
            .padding(horizontal = XLargeDp, vertical = SmallDp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = headerTitle,
            style = MaterialTheme.typography.titleMedium
        )
        Icon(
            imageVector = Forward,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            contentDescription = null
        )
    }

}

@Composable
fun MovieRow(
    modifier: Modifier = Modifier,
    movies: List<BaseContent>,
    navigateToMovieDetails: (BaseContent) -> Unit = {}
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(
            vertical = MediumDp,
            horizontal = LargeDp
        ),
        horizontalArrangement = Arrangement.spacedBy(MediumDp)
    ) {
        items(items = movies, key = { it.id }) { movie ->
            MovieItem(
                modifier = Modifier
                    .height(MOVIE_IMAGE_SIZE_DP)
                    .aspectRatio(2 / 3f)
                    .clickable {
                        navigateToMovieDetails(movie)
                    },
                imageUrl = movie.posterPath,
                title = movie.title,
            )
        }
    }
}


@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
) {
    Column (
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
        ) {
            ContentImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shapes.small),
                movieImageUrl = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieScreenPreview() {
    val popularMovies = listOf(
        Movie(id = 0, title = "popular movie  1"),
        Movie(id = 1, title = "popular movie 2"),
        Movie(id = 2, title = "popular movie 3"),
        Movie(id = 3, title = "popular movie 4")
    )

    val nowPlayingMovies = listOf(
        Movie(id = 0, title = "nowplaying movie  1"),
        Movie(id = 1, title = "nowplaying movie 2"),
        Movie(id = 2, title = "nowplaying movie 3"),
        Movie(id = 3, title = "nowplaying movie 4")
    )

    val popularSeries = listOf(
        TvSeries(id = 0, title = "popular series 1"),
        TvSeries(id = 1, title = "popular series 2"),
        TvSeries(id = 2, title = "popular series 3"),
        TvSeries(id = 3, title = "popular series 4")
    )

    val nowPlayingSeries = listOf(
        TvSeries(id = 0, title = "nowplaying series 1"),
        TvSeries(id = 1, title = "nowplaying series 2"),
        TvSeries(id = 2, title = "nowplaying series 3"),
        TvSeries(id = 3, title = "nowplaying series 4")
    )

    val state = MoviesState(
        isLoading = false,
        movies = AllMovies(nowPlayingMovies, popularMovies),
        tvSeries = AllTvSeries(nowPlayingSeries, popularSeries)
    )

    MoviesScreen(
        state = state,
        navigateToMovieDetails = {},
        onCategoryClicked = {},
        onRetryClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(
        imageUrl = "imageUrl",
        title = "Sample Title",
    )
}
