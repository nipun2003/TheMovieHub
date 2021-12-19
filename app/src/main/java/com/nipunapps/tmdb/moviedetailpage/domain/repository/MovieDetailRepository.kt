package com.nipunapps.tmdb.moviedetailpage.domain.repository

import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.moviedetailpage.domain.model.MovieDetailModel
import com.nipunapps.tmdb.moviedetailpage.domain.model.TVDetailModel
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {

    fun getMovieDetail(id : Int) : Flow<Resource<MovieDetailModel>>

    fun getTvDetail(id : Int) : Flow<Resource<TVDetailModel>>
}