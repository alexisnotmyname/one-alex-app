package com.alexc.ph.onealexapp.ui.movies.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexc.ph.domain.model.ContentType
import com.alexc.ph.domain.model.Search
import com.alexc.ph.onealexapp.ui.components.ContentImage
import com.alexc.ph.onealexapp.ui.theme.shapes

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    searchItem: Search,
    onSearchItemClick: (Search) -> Unit
) {
    Card(
        onClick = {
            onSearchItemClick(searchItem)
        },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier,
    ) {
        val title = searchItem.title.ifBlank { searchItem.name }
        val mediaType = searchItem.contentType.value
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            val imageUrl = when (searchItem.contentType) {
                ContentType.TV, ContentType.MOVIE -> searchItem.posterPath
                ContentType.PERSON -> searchItem.profilePath
            }

            Box(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                ContentImage(
                    movieImageUrl = imageUrl,
                    contentDescription = title,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(180.dp)
                        .aspectRatio(2/3f)
                        .clip(shapes.small),
                )

                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = mediaType ?: "",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                )

                if(searchItem.overview.isNotBlank()) {
                    Text(
                        text = searchItem.overview,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun SearchMovieItemsPreview() {
    val search = Search(
        id = 1,
        name = "Avengers",
        title = "Avengers",
        contentType = ContentType.MOVIE,
        overview = "Description of the movie",
    )
    SearchItem(
        searchItem = search,
        onSearchItemClick = {

        }
    )
}