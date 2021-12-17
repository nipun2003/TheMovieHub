package com.nipunapps.tmdb.homepage.domain.use_cases

import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.domain.models.TrendingModel
import com.nipunapps.tmdb.homepage.domain.repository.MoviesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrending @Inject constructor(
    private val moviesApi: MoviesApi
) {
    operator fun invoke(type:String,time:String) : Flow<Resource<TrendingModel>>{
        return moviesApi.getTrending(type,time)
    }
}