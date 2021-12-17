package com.nipunapps.tmdb.homepage.domain.models

import com.nipunapps.tmdb.homepage.data.remote.dto.trending.Result

data class TrendingModel(
    val results : List<Result>
)
