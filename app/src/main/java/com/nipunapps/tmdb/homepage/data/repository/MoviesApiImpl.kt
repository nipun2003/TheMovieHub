package com.nipunapps.tmdb.homepage.data.repository

import android.util.Log
import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.data.remote.HomeApi
import com.nipunapps.tmdb.homepage.domain.models.TrendingModel
import com.nipunapps.tmdb.homepage.domain.models.UpcomingModel
import com.nipunapps.tmdb.homepage.domain.repository.MoviesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MoviesApiImpl(
    private val api: HomeApi
) : MoviesApi {
    override fun getUpcomingMovies(): Flow<Resource<UpcomingModel>> = flow {
        emit(Resource.Loading<UpcomingModel>())
        try {
            val res = api.getUpcoming().toUpcomingModel()
            emit(Resource.Success<UpcomingModel>(data = res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<UpcomingModel>(
                    message = "Something went wrong",
                )
            )
        } catch (e: IOException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<UpcomingModel>(
                    message = "Couldn't reach server, check your internet connection.",
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<UpcomingModel>(
                    message = e.message.toString(),
                )
            )
        }
    }

    override fun getNowPlaying(): Flow<Resource<UpcomingModel>> = flow {
        emit(Resource.Loading<UpcomingModel>())
        try {
            val res = api.getNowPlaying().toNowPlayingModel()
            emit(Resource.Success<UpcomingModel>(data = res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<UpcomingModel>(
                    message = "Something went wrong",
                )
            )
        } catch (e: IOException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<UpcomingModel>(
                    message = "Couldn't reach server, check your internet connection.}",
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<UpcomingModel>(
                    message = e.message.toString(),
                )
            )
        }
    }

    override fun getTrending(type: String, time: String): Flow<Resource<TrendingModel>> = flow {
        emit(Resource.Loading<TrendingModel>())
        try {
            val res = api.getTrending(type,time).toTrendingModel()
            emit(Resource.Success<TrendingModel>(data = res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<TrendingModel>(
                    message = "Something went wrong",
                )
            )
        } catch (e: IOException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<TrendingModel>(
                    message = "Couldn't reach server, check your internet connection.",
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<TrendingModel>(
                    message = e.message.toString(),
                )
            )
        }
    }
}