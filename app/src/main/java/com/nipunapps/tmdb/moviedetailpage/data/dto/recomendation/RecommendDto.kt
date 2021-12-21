package com.nipunapps.tmdb.moviedetailpage.data.dto.recomendation

import com.nipunapps.tmdb.moviedetailpage.domain.model.RecommendModel

data class RecommendDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

