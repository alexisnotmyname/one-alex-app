package com.alexc.ph.onealexapp.ui.movies

sealed interface MoviesAction {
    data object OnRetryClicked : MoviesAction
}