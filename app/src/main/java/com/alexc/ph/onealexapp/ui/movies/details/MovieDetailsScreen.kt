package com.alexc.ph.onealexapp.ui.movies.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexc.ph.domain.model.ContentItem
import com.alexc.ph.domain.model.ContentType
import com.alexc.ph.domain.model.Genre
import com.alexc.ph.domain.model.Movie
import com.alexc.ph.domain.model.Season
import com.alexc.ph.domain.model.TvSeries
import com.alexc.ph.onealexapp.ext.toSeasonString
import com.alexc.ph.onealexapp.ui.components.ContentBackgroundImage
import com.alexc.ph.onealexapp.ui.components.ContentImage
import com.alexc.ph.onealexapp.ui.components.GenericErrorScreen
import com.alexc.ph.onealexapp.ui.components.GenericTopAppBar
import com.alexc.ph.onealexapp.ui.components.LoadingScreen
import com.alexc.ph.onealexapp.ui.constants.LargeDp
import com.alexc.ph.onealexapp.ui.constants.MediumDp
import com.alexc.ph.onealexapp.ui.constants.MovieOverviewBodyTextStyle
import com.alexc.ph.onealexapp.ui.constants.SmallDp
import com.alexc.ph.onealexapp.ui.constants.TOP_APP_BAR_HEIGHT_DP
import com.alexc.ph.onealexapp.ui.constants.WatchNowButtonTextStyle
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme
import com.alexc.ph.onealexapp.ui.util.verticalGradientScrim

@Composable
fun MovieDetailsScreen(
    navigateBack: () -> Unit,
    onWatchClick: (title: String) -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val movieDetailsUiState by viewModel.moviesUiState.collectAsStateWithLifecycle()
    when(movieDetailsUiState) {
        is MovieDetailsUiState.Error -> GenericErrorScreen()
        MovieDetailsUiState.Loading -> LoadingScreen()
        is MovieDetailsUiState.Success -> {
            val content = (movieDetailsUiState as MovieDetailsUiState.Success).contentItem
            MovieDetailsScreen(
                content = content,
                navigateBack = navigateBack,
                onWatchClick = onWatchClick
            )
        }
    }

}

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    content: ContentItem,
    navigateBack: () -> Unit = {},
    onWatchClick: (title: String) -> Unit = {}
) {
    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = {
            GenericTopAppBar(
                navigateBack = navigateBack,
                modifier = Modifier
                    .height(TOP_APP_BAR_HEIGHT_DP)
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            when(content) {
                is ContentItem.MovieItem -> {
                    MovieBackground(
                        imageUrl = content.movie.backdropPath,
                        contentDescription = content.movie.title,
                        modifier = modifier
                            .fillMaxSize()
                    )
                    MovieDetailsContent(
                        movie = content.movie,
                        onWatchClick = onWatchClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(contentPadding)
                    )
                }
                is ContentItem.TvSeriesItem -> {
                    MovieBackground(
                        imageUrl = content.tvSeries.backdropPath,
                        contentDescription = content.tvSeries.title,
                        modifier = modifier
                            .fillMaxSize()
                    )
                    TvSeriesDetailsContent(
                        tvSeries = content.tvSeries,
                        onWatchClick = onWatchClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(contentPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MovieDetailsContent(
    modifier: Modifier = Modifier,
    movie: Movie,
    onWatchClick: (title: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .verticalGradientScrim(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.50f),
                startYPercentage = 0f,
                endYPercentage = 1f
            )
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(LargeDp))
            MoviePoster(
                podcastImageUrl = movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .width(280.dp)
                    .aspectRatio(2 / 3f)
            )
            Spacer(modifier = Modifier.height(MediumDp))
            WatchButton(
                title = movie.title,
                onWatchClick = onWatchClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MediumDp))
            MovieDescription(
                movie = movie,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MediumDp)
            )
            Spacer(modifier = Modifier.height(SmallDp))
        }
    }
}

@Composable
fun TvSeriesDetailsContent(
    modifier: Modifier = Modifier,
    tvSeries: TvSeries,
    onWatchClick: (title: String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .verticalGradientScrim(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.50f),
                startYPercentage = 0f,
                endYPercentage = 1f
            )
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(LargeDp))
            MoviePoster(
                podcastImageUrl = tvSeries.posterPath,
                contentDescription = tvSeries.title,
                modifier = Modifier
                    .width(280.dp)
                    .aspectRatio(2 / 3f)
            )
            Spacer(modifier = Modifier.height(MediumDp))
            WatchButton(
                title = tvSeries.title,
                onWatchClick = onWatchClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MediumDp))
            TvSeriesDescription(
                tvSeries = tvSeries,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MediumDp)
            )
            Spacer(modifier = Modifier.height(SmallDp))
        }
    }
}

@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    podcastImageUrl: String,
    contentDescription: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        ContentImage(
            movieImageUrl = podcastImageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun WatchButton(
    modifier: Modifier = Modifier,
    title: String,
    onWatchClick: (title: String) -> Unit
) {
    Button(
        onClick = { onWatchClick(title) },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurface),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(48.dp)
    ) {
        Text(
            text = "Watch $title",
            style = WatchNowButtonTextStyle,
            color = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun MovieDetailsGenre(
    modifier: Modifier = Modifier,
    genres: List<Genre>
) {
    LazyRow (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    )  {
        items(items = genres, key = { genre -> genre.id }) { genre ->
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(4.dp))
                    .padding(4.dp)
            ) {
                Text(text = genre.name, color = MaterialTheme.colorScheme.surface, fontSize = 12.sp)
            }
        }
    }
}


@Composable
fun MovieBackground(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String
) {
    ContentBackgroundImage(
        url = imageUrl,
        contentDescription = contentDescription,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        modifier = modifier
    )
}

@Composable
private fun MovieDescription(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(MediumDp))
        MovieDetailsGenre(modifier = Modifier, genres = movie.genres)
        Spacer(modifier = Modifier.height(MediumDp))
        MovieOtherInfo(
            movie = movie,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = movie.overview,
            style = MovieOverviewBodyTextStyle,
            modifier = Modifier.padding(MediumDp)
        )
    }
}

@Composable
private fun TvSeriesDescription(
    modifier: Modifier = Modifier,
    tvSeries: TvSeries
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(MediumDp))
        MovieDetailsGenre(modifier = Modifier, genres = tvSeries.genres)
        Spacer(modifier = Modifier.height(MediumDp))
        TvSeriesOtherInfo(
            tvSeries = tvSeries,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = tvSeries.overview,
            style = MovieOverviewBodyTextStyle,
            modifier = Modifier.padding(MediumDp)
        )
    }
}

@Composable
private fun MovieOtherInfo(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = movie.releaseDate,
            style = MovieOverviewBodyTextStyle,
            modifier = Modifier.padding(MediumDp)
        )

        Text(
            text = "${movie.runTime} min",
            style = MovieOverviewBodyTextStyle,
            modifier = Modifier.padding(MediumDp)
        )
    }
}

@Composable
private fun TvSeriesOtherInfo(
    modifier: Modifier = Modifier,
    tvSeries: TvSeries
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = tvSeries.firstAirDate,
            style = MovieOverviewBodyTextStyle,
            modifier = Modifier.padding(MediumDp)
        )

        Text(
            text = tvSeries.seasons.toSeasonString(),
            style = MovieOverviewBodyTextStyle,
            modifier = Modifier.padding(MediumDp)
        )
    }
}

@Preview
@Composable
fun MovieDetailsPreview() {
    OneAlexAppTheme {
        val movie = Movie(
            id = 0,
            title = "Dummy Title 1",
            contentType = ContentType.MOVIE,
            overview = "Test description",
            genres = listOf(
                Genre(1, "Comedy"),
                Genre(2, "Action")
            ),
            releaseDate = "2024-10-13",
            runTime = 100
        )

        val tvSeries = TvSeries(
            id = 0,
            title = "Dummy Title 1",
            contentType = ContentType.TV,
            overview = "Test description",
            genres = listOf(
                Genre(1, "Comedy"),
                Genre(2, "Action")
            ),
            firstAirDate = "2024-10-13",
            seasons = listOf(
                Season(0, "Season 1"),Season(1, "Season 2")
            )
        )
        MovieDetailsScreen(
            content = ContentItem.TvSeriesItem(tvSeries),
            modifier = Modifier
        )
    }
}