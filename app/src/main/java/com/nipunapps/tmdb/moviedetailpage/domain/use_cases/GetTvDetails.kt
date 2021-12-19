package com.nipunapps.tmdb.moviedetailpage.domain.use_cases

import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.moviedetailpage.domain.model.TVDetailModel
import com.nipunapps.tmdb.moviedetailpage.domain.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvDetails @Inject constructor(
    private val repository: MovieDetailRepository
) {
    operator fun invoke(id : Int) : Flow<Resource<TVDetailModel>>{
        return repository.getTvDetail(id)
    }
}