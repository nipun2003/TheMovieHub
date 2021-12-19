package com.nipunapps.tmdb.feature_search.data.remote.dto

import com.nipunapps.tmdb.feature_search.domain.model.QueryResultModel

data class QueryResultDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
){
    fun toQueryResultModel() : QueryResultModel{
        return QueryResultModel(
            totalResult = total_results,
            results = results.map { it.toSingleSearchResult() }
        )
    }
}