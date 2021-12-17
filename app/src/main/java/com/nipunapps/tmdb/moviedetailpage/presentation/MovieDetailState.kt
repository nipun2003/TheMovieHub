package com.nipunapps.tmdb.moviedetailpage.presentation

import com.nipunapps.tmdb.moviedetailpage.domain.model.MovieDetailModel

data class MovieDetailState(
    val isLoading : Boolean = false,
    val data : MovieDetailModel? = null,
    val message : String? = null
)
