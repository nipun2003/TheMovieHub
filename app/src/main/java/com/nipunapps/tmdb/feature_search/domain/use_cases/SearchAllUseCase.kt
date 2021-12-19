package com.nipunapps.tmdb.feature_search.domain.use_cases

import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.feature_search.domain.model.QueryResultModel
import com.nipunapps.tmdb.feature_search.domain.repository.QueryResultRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchAllUseCase @Inject constructor(
    private val queryResultRepository: QueryResultRepository
) {
    operator fun invoke(query : String) : Flow<Resource<QueryResultModel>>{
        return  queryResultRepository.searchQuery(query)
    }
}