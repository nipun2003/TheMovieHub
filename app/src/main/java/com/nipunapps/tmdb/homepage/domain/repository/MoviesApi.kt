package com.nipunapps.tmdb.homepage.domain.repository

import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.domain.models.PresentationModel
import kotlinx.coroutines.flow.Flow

interface MoviesApi {
    fun getUpcomingMovies(): Flow<Resource<List<PresentationModel>>>
    fun getPosterMovie(): Flow<Resource<PresentationModel>>
    fun getTrending(type : String,time : String): Flow<Resource<List<PresentationModel>>>
    fun getNowPlaying() : Flow<Resource<List<PresentationModel>>>
    fun getPopularity(type: String) : Flow<Resource<List<PresentationModel>>>
}