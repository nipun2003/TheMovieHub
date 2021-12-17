package com.nipunapps.tmdb.ui

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home")
    object SearchResult: Screen("query")
    object MovieDetailScreen : Screen("details")
}