package com.nipunapps.tmdb.moviedetailpage.domain.use_cases

import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.moviedetailpage.domain.model.MovieDetailModel
import com.nipunapps.tmdb.moviedetailpage.domain.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(id : Int) : Flow<Resource<MovieDetailModel>>{
        return movieDetailRepository.getMovieDetail(id)
    }
}