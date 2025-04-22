package com.alexc.ph.onealexapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme

@Composable
fun ContentImage(
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

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        when (imagePainterState) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(modifier = Modifier)
            }
            else -> {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .fillMaxSize()

                )
            }
        }

        Image(
            painter = imageLoader,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ContentBackgroundImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String,
    color: Color,
    contentScale: ContentScale = ContentScale.Crop
) {
    var imagePainterState by remember {
        mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty)
    }

    val imageLoader = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        onState = { state -> imagePainterState = state }
    )

    when (imagePainterState) {
        is AsyncImagePainter.State.Loading,
        is AsyncImagePainter.State.Error -> {
            CircularProgressIndicator(
                modifier = Modifier
            )
        }
        else -> {
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxSize()

            )
        }
    }

    ImageBackGround(
        modifier = modifier,
        imageLoader = imageLoader,
        contentDescription = contentDescription,
        overlay = {
            drawRect(color)
        },
        contentScale = contentScale
    )
}

@Composable
fun ImageBackGround(
    modifier: Modifier = Modifier,
    imageLoader: AsyncImagePainter,
    contentDescription: String,
    overlay: DrawScope.() -> Unit,
    contentScale: ContentScale = ContentScale.Crop,
) {
    Image(
        painter = imageLoader,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
            .fillMaxWidth()
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    overlay()
                }
            }
    )
}

@Preview
@Composable
fun ContentImagePreview() {
    OneAlexAppTheme {
        ContentImage(
            movieImageUrl = "",
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(330.dp)
                .aspectRatio(2/3f)
        )
    }
}

@Preview
@Composable
fun ContentBackgroundImagePreview() {
    OneAlexAppTheme {
        ContentBackgroundImage(
            url = "",
            contentDescription = "",
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
            modifier = Modifier.fillMaxSize()
        )
    }
}
