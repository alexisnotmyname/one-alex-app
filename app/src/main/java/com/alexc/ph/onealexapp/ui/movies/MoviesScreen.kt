package com.alexc.ph.onealexapp.ui.movies

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.alexc.ph.data.model.movies.Movie
import com.alexc.ph.onealexapp.R
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    moviesMoviesViewModel: MoviesViewModel = hiltViewModel()
) {
    val moviesState by moviesMoviesViewModel.moviesUiState.collectAsStateWithLifecycle()
    if (moviesState is MoviesUiState.Success) {
        val movies = (moviesState as MoviesUiState.Success).movies
        MovieScreen(
            modifier = modifier,
            movies = movies
        )
    }
}

@Composable
fun MovieScreen(
    modifier: Modifier = Modifier,
    movies: List<Movie>
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(movies) { movie ->
            MovieItem(
                modifier = Modifier.height(160.dp),
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
    println("movie item: $title \n$imageUrl")
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            MovieImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium),
                movieImageUrl = imageUrl,
                contentDescription = title,
            )
        }
        Text(
            text = title,
            textAlign = TextAlign.Center
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
//    println(movieImageUrl)
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
        initialValue = 0f,
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
            Movie(id = 3, title = "Dummy Title 4"),
            Movie(id = 4, title = "Dummy Title 5"),
            Movie(id = 5, title = "Dummy Title 6"),
            Movie(id = 6, title = "Dummy Title 7"),
            Movie(id = 7, title = "Dummy Title 8"),
        )
        MovieScreen(
            movies = movies
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