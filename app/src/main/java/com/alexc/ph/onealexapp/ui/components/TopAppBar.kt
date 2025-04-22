package com.alexc.ph.onealexapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexc.ph.onealexapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneAlexTopAppBar(
    modifier: Modifier = Modifier,
    isSearch: Boolean = false,
    onNavigationClick: () -> Unit,
    titleContent: @Composable () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            titleContent()
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = if(isSearch) OneAlexAppIcons.Back else OneAlexAppIcons.Search,
                    contentDescription = stringResource(if(isSearch) R.string.cd_back else R.string.search),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
        modifier = modifier
    )
}

@Composable
fun GenericTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigation: @Composable () -> Unit = {},
    action: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigation()
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        action()
    }
}

@Preview(showBackground = true)
@Composable
fun OneAlexTopAppBarTitlePreview() {
    OneAlexTopAppBar(
        onNavigationClick = {}
    ) {
        Text(text = stringResource(R.string.todo))
    }
}

@Preview(showBackground = true)
@Composable
fun OneAlexTopAppBarSearchPreview() {
    OneAlexTopAppBar(
        onNavigationClick = {}
    ) {
        SearchTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = "",
            onSearchQueryChange = {},
            onImeSearch = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GenericTopAppBarPreview() {
    GenericTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = "Preview",
        navigation = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null
                )
            }
        },
        action = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null
                )
            }
        },
    )
}

