package com.nipunapps.tmdb.homepage.domain.models

import com.nipunapps.tmdb.homepage.data.remote.dto.upcoming.Result

data class UpcomingModel(
    val results: List<Result> = emptyList()
)