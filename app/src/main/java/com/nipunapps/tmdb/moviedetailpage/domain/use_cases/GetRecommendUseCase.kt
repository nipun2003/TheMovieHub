package com.nipunapps.tmdb.moviedetailpage.domain.use_cases

import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.moviedetailpage.domain.model.RecommendModel
import com.nipunapps.tmdb.moviedetailpage.domain.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(type:String,id : Int): Flow<Resource<List<RecommendModel>>> {
        return movieDetailRepository.getRecommendation(type,id)
    }
}