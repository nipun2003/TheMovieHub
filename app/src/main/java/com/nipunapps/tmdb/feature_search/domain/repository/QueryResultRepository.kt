package com.nipunapps.tmdb.feature_search.domain.repository

import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.feature_search.domain.model.QueryResultModel
import kotlinx.coroutines.flow.Flow

interface QueryResultRepository {

    fun searchQuery(query : String) : Flow<Resource<QueryResultModel>>
}