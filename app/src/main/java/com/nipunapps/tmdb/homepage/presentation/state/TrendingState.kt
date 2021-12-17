package com.nipunapps.tmdb.homepage.presentation.state

import com.nipunapps.tmdb.homepage.data.remote.dto.trending.Result

data class TrendingState(
    val isLoading : Boolean = false,
    val data : List<Result> = emptyList(),
    val message : String = ""
)
