package com.nipunapps.tmdb.feature_search.presentation

import com.nipunapps.tmdb.feature_search.domain.model.SingleSearchResult

data class SearchQueryState(
    val isLoading : Boolean = false,
    val data : List<SingleSearchResult> = emptyList(),
    val message : String? = null,
    val isFromSuccess : Boolean = false,
    val isRecent : Boolean = false
)