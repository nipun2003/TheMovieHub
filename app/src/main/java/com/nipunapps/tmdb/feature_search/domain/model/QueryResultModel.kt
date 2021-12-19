package com.nipunapps.tmdb.feature_search.domain.model

data class QueryResultModel(
    val totalResult: Int,
    val results: List<SingleSearchResult>
)