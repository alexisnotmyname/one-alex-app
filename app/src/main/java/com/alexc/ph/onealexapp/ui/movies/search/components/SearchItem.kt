package com.alexc.ph.onealexapp.ui.movies.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexc.ph.domain.model.Search

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    searchItem: Search,
    onSearchItemClick: () -> Unit
) {
    Card(
        onClick = onSearchItemClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {

        if(searchItem.backdropPath.isNotBlank()) {
//            Row {
//                NewsResourceHeaderImage(userNewsResource.headerImageUrl)
//            }
        }
        Column(modifier = Modifier.padding(8.dp)) {
            val title = searchItem.title.ifBlank { searchItem.name }
            Text(title, style = MaterialTheme.typography.headlineSmall, modifier = modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchMovieItemsPreview() {
    val search = Search(
        id = 1,
        name = "Avengers",
        title = "Avengers"
    )
    SearchItem(
        searchItem = search,
        onSearchItemClick = {

        }
    )
}