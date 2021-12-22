package com.nipunapps.tmdb.homepage.data.remote.dto.popular

import com.nipunapps.tmdb.homepage.data.remote.dto.upcoming.Result

data class PopularityDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)