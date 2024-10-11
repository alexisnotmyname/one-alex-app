package com.alexc.ph.onealexapp.ui.movies

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.alexc.ph.domain.model.CombinedMovies
import com.alexc.ph.domain.model.Movies.Movie
import com.alexc.ph.onealexapp.R
import com.alexc.ph.onealexapp.ui.constants.MediumDp
import com.alexc.ph.onealexapp.ui.constants.MovieTitleTextStyle
import com.alexc.ph.onealexapp.ui.constants.SmallDp
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme
import com.alexc.ph.onealexapp.ui.theme.shapes

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    moviesMoviesViewModel: MoviesViewModel = hiltViewModel(),
    navigateToMovieDetails: (Movie) -> Unit = {}
) {
    val moviesState by moviesMoviesViewModel.moviesUiState.collectAsStateWithLifecycle()
    if (moviesState is MoviesUiState.Success) {
        val movies = (moviesState as MoviesUiState.Success).movies

        MoviesScreen(
            modifier = modifier.fillMaxWidth(),
            movies = movies,
            navigateToMovieDetails = navigateToMovieDetails
        )
    }
}

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    movies: CombinedMovies,
    navigateToMovieDetails: (Movie) -> Unit = {}
) {
    Column (
        modifier.fillMaxSize()
    ){
        MovieRow(
            modifier = modifier,
            movies = movies.nowPlaying,
            navigateToMovieDetails = navigateToMovieDetails
        )
        MovieRow(
            modifier = modifier,
            movies = movies.popular,
            navigateToMovieDetails = navigateToMovieDetails
        )
    }

}

@Composable
fun PopularMovies(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    navigateToMovieDetails: (Movie) -> Unit = {}
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
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

@Composable
fun NowPlayingMovies(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    navigateToMovieDetails: (Movie) -> Unit = {}
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
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
    movies: List<Movie>,
    navigateToMovieDetails: (Movie) -> Unit = {}
) {
    items(items = movies, key = {it.id}) { movie ->
        MovieItem(
            modifier = Modifier
                .width(MOVIE_IMAGE_SIZE_DP)
                .clickable {
                    navigateToMovieDetails(movie)
                },
            imageUrl = movie.posterPath,
            title = movie.title,
        )
    }
}

private val MOVIE_IMAGE_SIZE_DP = 160.dp

@Composable
fun MovieRow(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    navigateToMovieDetails: (Movie) -> Unit = {}
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 8.dp,
            end = 16.dp,
            bottom = 24.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            MovieItem(
                modifier = Modifier
                    .width(MOVIE_IMAGE_SIZE_DP)
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
        modifier.semantics(mergeDescendants = true) {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            MovieImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shapes.medium),
                movieImageUrl = imageUrl,
                contentDescription = title,
            )
        }
        Text(
            text = title,
            style = MovieTitleTextStyle,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = MediumDp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun MovieImage(
    modifier: Modifier = Modifier,
    movieImageUrl: String,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop,
) {

    var imagePainterState by remember {
        mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty)
    }

    val imageLoader = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(movieImageUrl)
            .crossfade(true)
            .build(),
        contentScale = contentScale,
        onState = { state -> imagePainterState = state }
    )

    val infiniteTransition = rememberInfiniteTransition(label = "infinite loading")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){

        when (imagePainterState) {
            is AsyncImagePainter.State.Loading,
            is AsyncImagePainter.State.Error -> {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    alpha = alpha,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            else -> {
                Box(
                    modifier = Modifier
                        .background(Color.Gray)
                        .fillMaxSize()

                )
            }
        }

        Image(
            painter = imageLoader,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
        )
    }
}

@Preview
@Composable
fun MovieScreenPreview() {
    OneAlexAppTheme {
        val movies = listOf(
            Movie(id = 0, title = "Dummy Title 1"),
            Movie(id = 1, title = "Dummy Title 2"),
            Movie(id = 2, title = "Dummy Title 3"),
            Movie(id = 3, title = "Dummy Title 4")
        )
        val combinedMovies = CombinedMovies(movies, movies)
        MoviesScreen(
            movies = combinedMovies
        )
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    OneAlexAppTheme {
        MovieItem(
            imageUrl = "imageUrl",
            title = "Sample Title",
        )
    }
}

fun LazyGridScope.fullWidthItem(
    key: Any? = null,
    contentType: Any? = null,
    content: @Composable LazyGridItemScope.() -> Unit
) = item(
    span = { GridItemSpan(this.maxLineSpan) },
    key = key,
    contentType = contentType,
    content = content
)