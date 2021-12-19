package com.nipunapps.tmdb.feature_search.data.repository

import android.util.Log
import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.feature_search.data.local.QueryInfoDao
import com.nipunapps.tmdb.feature_search.data.local.entity.QueryResultEntity
import com.nipunapps.tmdb.feature_search.domain.model.QueryResultModel
import com.nipunapps.tmdb.feature_search.domain.repository.QueryResultRepository
import com.nipunapps.tmdb.homepage.data.remote.HomeApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class QueryResultRepositoryImpl(
    private val api: HomeApi,
    private val dao : QueryInfoDao
) : QueryResultRepository {

    override fun searchQuery(query: String): Flow<Resource<QueryResultModel>> = flow {
        emit(Resource.Loading<QueryResultModel>())
        try {
            val res = api.searchAll(query).toQueryResultModel()
            dao.insertQuery(QueryResultEntity(query = query))
            emit(Resource.Success<QueryResultModel>(data = res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<QueryResultModel>(
                    message = "Something went wrong",
                )
            )
        } catch (e: IOException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<QueryResultModel>(
                    message = "Couldn't reach server, check your internet connection.",
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<QueryResultModel>(
                    message = e.message.toString(),
                )
            )
        }
    }
}