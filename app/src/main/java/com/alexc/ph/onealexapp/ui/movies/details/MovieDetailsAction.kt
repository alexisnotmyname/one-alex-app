package com.alexc.ph.onealexapp.ui.movies.details


sealed interface MovieDetailsAction {
    data object OnRetryClicked : MovieDetailsAction
}
