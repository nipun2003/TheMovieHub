package com.nipunapps.tmdb.homepage.domain.repository

import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.domain.models.TrendingModel
import com.nipunapps.tmdb.homepage.domain.models.UpcomingModel
import kotlinx.coroutines.flow.Flow

interface MoviesApi {
    fun getUpcomingMovies(): Flow<Resource<UpcomingModel>>
    fun getNowPlaying(): Flow<Resource<UpcomingModel>>
    fun getTrending(type : String,time : String): Flow<Resource<TrendingModel>>
}