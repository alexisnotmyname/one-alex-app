package com.alexc.ph.onealexapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexc.ph.onealexapp.R
import com.alexc.ph.onealexapp.ui.components.OneAlexAppIcons.Back
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneAlexTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        colors = colors,
        modifier = modifier
    )
}

@Composable
fun GenericTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigateBack: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = navigateBack) {
            Icon(
                imageVector = Back,
                contentDescription = stringResource(R.string.cd_back)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
fun GenericTopAppBarPreview() {
    OneAlexAppTheme {
        GenericTopAppBar(
            title = "Preview",
            navigateBack = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

