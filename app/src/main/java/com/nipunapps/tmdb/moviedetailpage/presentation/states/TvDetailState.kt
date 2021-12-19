package com.nipunapps.tmdb.moviedetailpage.presentation.states

import com.nipunapps.tmdb.moviedetailpage.domain.model.TVDetailModel

data class TvDetailState(
    val isLoading : Boolean = false,
    val message : String? = null,
    val data : TVDetailModel?  = null
)
