package com.nipunapps.tmdb.homepage.presentation.state

import com.nipunapps.tmdb.homepage.data.remote.dto.upcoming.Result

data class UpcomingState(
    val isLoading : Boolean = false,
    val results : List<Result> = emptyList(),
    val message : String = ""
)
