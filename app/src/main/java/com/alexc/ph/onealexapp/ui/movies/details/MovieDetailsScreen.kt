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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.alexc.ph.onealexapp.ui.constants.SmallDp
import com.alexc.ph.onealexapp.ui.constants.TOP_APP_BAR_HEIGHT_DP
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme
import com.alexc.ph.onealexapp.ui.theme.shapes
import com.alexc.ph.onealexapp.ui.theme.starYellow
import com.alexc.ph.onealexapp.ui.util.verticalGradientScrim
import org.koin.androidx.compose.koinViewModel
import java.util.Locale
import kotlin.math.min

@Composable
fun MovieDetailsScreenRoot(
    navigateBack: () -> Unit,
    onWatchClick: (title: String) -> Unit,
    viewModel: MovieDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MovieDetailsScreen(
        state = state,
        navigateBack = navigateBack,
        onWatchClick = onWatchClick,
        onRetry = {

        }
    )
}

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    state: MovieDetailsState,
    navigateBack: () -> Unit,
    onWatchClick: (title: String) -> Unit,
    onRetry: () -> Unit
) {
    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = {
            GenericTopAppBar(
                navigation = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
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
            if(state.isLoading) {
                LoadingScreen()
            } else {
                when {
                    state.error.isNotBlank() || state.contentItem == null -> GenericErrorScreen(onRetry = {})
                    else -> {
                        val content = state.contentItem
                        val (backdropPath, posterPath, title) = when(content) {
                            is ContentItem.MovieItem -> {
                                Triple(content.movie.backdropPath, content.movie.posterPath, content.movie.title)
                            }
                            is ContentItem.TvSeriesItem -> {
                                Triple(content.tvSeries.backdropPath, content.tvSeries.posterPath, content.tvSeries.title)
                            }
                        }
                        ContentBackgroundImage(
                            url = backdropPath,
                            contentDescription = title,
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        DetailsContent(
                            posterPath = posterPath,
                            title = title,
                            content = content,
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
}

@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    posterPath: String,
    title: String,
    content: ContentItem,
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
            Spacer(modifier = Modifier.height(8.dp))
            ContentImage(
                movieImageUrl = posterPath,
                contentDescription = title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(320.dp)
                    .padding(16.dp)
                    .aspectRatio(2/3f)
                    .clip(shapes.small),
            )
            Spacer(modifier = Modifier.height(8.dp))
//            Button(
//                onClick = { onWatchClick(title) },
//                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//            ) {
//                Text(
//                    text = "Watch $title",
//                    style = MaterialTheme.typography.titleMedium.copy(
//                        fontWeight = FontWeight.Bold
//                    ),
//                    color = MaterialTheme.colorScheme.onPrimary,
//                    textAlign = TextAlign.Center,
//                )
//            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold,
                )
                when(content) {
                    is ContentItem.MovieItem -> {
                        MovieDescription(
                            movie = content.movie,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    is ContentItem.TvSeriesItem -> {
                        TvSeriesDescription(
                            tvSeries = content.tvSeries,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(SmallDp))
        }
    }
}

@Composable
fun Genres(
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
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = genre.name,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
private fun MovieDescription(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(SmallDp))
        Genres(modifier = Modifier, genres = movie.genres)
        Spacer(modifier = Modifier.height(MediumDp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = movie.releaseDate,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "|",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "${movie.runTime} min",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "|",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Rounded.Star,
                tint = starYellow,
                contentDescription = null
            )
            Text(
                text = String.format(Locale.getDefault(), "%.1f", movie.voteAverage),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun TvSeriesDescription(
    modifier: Modifier = Modifier,
    tvSeries: TvSeries
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(SmallDp))
        Genres(modifier = Modifier, genres = tvSeries.genres)
        Spacer(modifier = Modifier.height(MediumDp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tvSeries.firstAirDate,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "|",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = tvSeries.seasons.toSeasonString(),
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "|",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Rounded.Star,
                tint = starYellow,
                contentDescription = null
            )
            Text(
                text = String.format(Locale.getDefault(), "%.1f", tvSeries.voteAverage),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = tvSeries.overview,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsPreview() {
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

    MovieDetailsScreen(
        state = MovieDetailsState(
            contentItem = ContentItem.MovieItem(movie),
            isLoading = false
        ),
        modifier = Modifier,
        navigateBack = {},
        onWatchClick = {},
        onRetry = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TvSeriesDetailsPreview() {
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
            Season(0, "Season 1"), Season(1, "Season 2")
        )
    )

    MovieDetailsScreen(
        state = MovieDetailsState(
            contentItem = ContentItem.TvSeriesItem(tvSeries),
            isLoading = false
        ),
        modifier = Modifier,
        navigateBack = {},
        onWatchClick = {},
        onRetry = {}
    )
}