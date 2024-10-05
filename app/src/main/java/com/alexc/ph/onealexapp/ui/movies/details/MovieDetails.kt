package com.alexc.ph.onealexapp.ui.movies.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alexc.ph.onealexapp.ui.components.PageUnderConstructionScreen
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme

@Composable
fun MovieDetails(
    modifier: Modifier = Modifier
) {
    PageUnderConstructionScreen(modifier = modifier)
}

@Preview
@Composable
fun MovieDetailsPreview() {
    OneAlexAppTheme {
        MovieDetails()
    }
}