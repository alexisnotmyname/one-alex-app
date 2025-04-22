package com.alexc.ph.onealexapp.ui.movies.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexc.ph.domain.model.BaseContent
import com.alexc.ph.domain.model.ContentType
import com.alexc.ph.onealexapp.ui.components.OneAlexTopAppBar
import com.alexc.ph.onealexapp.ui.components.SearchTextField
import com.alexc.ph.onealexapp.ui.movies.search.components.SearchItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreenRoot(
    searchViewModel: SearchViewModel = koinViewModel(),
    navigateBack: () -> Unit,
    navigateToDetails: (BaseContent) -> Unit
) {
    val state by searchViewModel.state.collectAsStateWithLifecycle()
    SearchScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        navigateBack = navigateBack,
        onSearchQueryChange = { query ->
            searchViewModel.onSearchQueryChange(query)
        },
        onClickItem = { searchItem ->
            if(searchItem.contentType != ContentType.PERSON) {
                navigateToDetails(searchItem)
            }
        }
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    navigateBack: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onClickItem: (BaseContent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        OneAlexTopAppBar(
            isSearch = true,
            onNavigationClick = navigateBack
        ) {
            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = state.query,
                onSearchQueryChange = onSearchQueryChange,
                onImeSearch = {

                }
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.searchResults, key = { it.id }) {
                SearchItem(
                    searchItem = it,
                    onSearchItemClick = { searchItem ->
                        onClickItem(searchItem)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        state = SearchState(),
        navigateBack = {},
        onSearchQueryChange = {},
        onClickItem = {}
    )
}