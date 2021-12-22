package com.nipunapps.tmdb.moviedetailpage.data.repository

import android.util.Log
import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.data.remote.HomeApi
import com.nipunapps.tmdb.moviedetailpage.domain.model.MovieDetailModel
import com.nipunapps.tmdb.moviedetailpage.domain.model.RecommendModel
import com.nipunapps.tmdb.moviedetailpage.domain.model.TVDetailModel
import com.nipunapps.tmdb.moviedetailpage.domain.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieDetailRepositoryImpl(
    private val api: HomeApi
) : MovieDetailRepository {

    override fun getMovieDetail(id: Int): Flow<Resource<MovieDetailModel>> = flow {
        emit(Resource.Loading<MovieDetailModel>())
        try {
            val res = api.getMovieDetails(id).toMovieDetailModel()
            emit(Resource.Success<MovieDetailModel>(data = res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<MovieDetailModel>(
                    message = "Something went wrong",
                )
            )
        } catch (e: IOException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<MovieDetailModel>(
                    message = "Couldn't reach server, check your internet connection.",
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<MovieDetailModel>(
                    message = e.message.toString(),
                )
            )
        }
    }

    override fun getTvDetail(id: Int): Flow<Resource<TVDetailModel>> = flow {
        emit(Resource.Loading<TVDetailModel>())
        try {
            val res = api.getTvDetails(id).toTvDetailModel()
            emit(Resource.Success<TVDetailModel>(data = res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<TVDetailModel>(
                    message = "Something went wrong",
                )
            )
        } catch (e: IOException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<TVDetailModel>(
                    message = "Couldn't reach server, check your internet connection.",
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<TVDetailModel>(
                    message = e.message.toString(),
                )
            )
        }
    }

    override fun getRecommendation(type: String, id: Int): Flow<Resource<List<RecommendModel>>> =
        flow {
            emit(Resource.Loading<List<RecommendModel>>())
            try {
                val res = api.getRecommendation(type,id).results.map { it.toRecommendModel() }
                emit(Resource.Success<List<RecommendModel>>(data = res))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<List<RecommendModel>>(
                        message = "Something went wrong",
                    )
                )
            } catch (e: IOException) {
                Log.e("Nipun", e.message.toString())
                emit(
                    Resource.Error<List<RecommendModel>>(
                        message = "Couldn't reach server, check your internet connection.",
                    )
                )
            } catch (e: Exception) {
                emit(
                    Resource.Error<List<RecommendModel>>(
                        message = e.message.toString(),
                    )
                )
            }
        }
}