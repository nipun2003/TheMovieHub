package com.nipunapps.tmdb.homepage.domain.use_cases

import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.domain.models.PresentationModel
import com.nipunapps.tmdb.homepage.domain.repository.MoviesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcoming @Inject constructor(
    private val moviesApi: MoviesApi
) {
    operator fun invoke():Flow<Resource<List<PresentationModel>>> {
        return  moviesApi.getUpcomingMovies()
    }

    fun getNowPlaying():Flow<Resource<List<PresentationModel>>>{
        val res = moviesApi.getNowPlaying()
        return moviesApi.getNowPlaying()
    }

    fun getPosterMovie():Flow<Resource<PresentationModel>>{
        return moviesApi.getPosterMovie()
    }

    fun getPopularity(type : String) : Flow<Resource<List<PresentationModel>>>{
        return moviesApi.getPopularity(type)
    }
}