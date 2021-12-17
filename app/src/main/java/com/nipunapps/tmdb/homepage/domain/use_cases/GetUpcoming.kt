package com.nipunapps.tmdb.homepage.domain.use_cases

import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.domain.models.UpcomingModel
import com.nipunapps.tmdb.homepage.domain.repository.MoviesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcoming @Inject constructor(
    private val moviesApi: MoviesApi
) {
    operator fun invoke():Flow<Resource<UpcomingModel>> {
        return  moviesApi.getUpcomingMovies()
    }

    fun getNowPlaying():Flow<Resource<UpcomingModel>>{
        return moviesApi.getNowPlaying()
    }
}