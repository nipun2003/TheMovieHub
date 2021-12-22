package com.nipunapps.tmdb.homepage.data.repository

import android.util.Log
import com.nipunapps.tmdb.core.Constants.MOVIE
import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.data.remote.HomeApi
import com.nipunapps.tmdb.homepage.domain.models.PresentationModel
import com.nipunapps.tmdb.homepage.domain.repository.MoviesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MoviesApiImpl(
    private val api: HomeApi
) : MoviesApi {
    override fun getUpcomingMovies(): Flow<Resource<List<PresentationModel>>> = flow {
        emit(Resource.Loading<List<PresentationModel>>())
        try {
            val res = api.getUpcoming().results.map { it.toPresentationModel(MOVIE) }
            emit(Resource.Success<List<PresentationModel>>(data = res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = "Something went wrong",
                )
            )
        } catch (e: IOException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = "Couldn't reach server, check your internet connection.",
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = e.message.toString(),
                )
            )
        }
    }

    override fun getPosterMovie(): Flow<Resource<PresentationModel>> = flow {
        emit(Resource.Loading<PresentationModel>())
        try {
            val res = api.getNowPlaying().toNowPlayingModel()
            emit(Resource.Success<PresentationModel>(data = res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<PresentationModel>(
                    message = "Something went wrong",
                )
            )
        } catch (e: IOException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<PresentationModel>(
                    message = "Couldn't reach server, check your internet connection.}",
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<PresentationModel>(
                    message = e.message.toString(),
                )
            )
        }
    }

    override fun getTrending(type: String, time: String): Flow<Resource<List<PresentationModel>>> = flow {
        emit(Resource.Loading<List<PresentationModel>>())
        try {
            val res = api.getTrending(type,time).results.map { it.toPresentationModel() }
            emit(Resource.Success<List<PresentationModel>>(data = res))
        } catch (e: HttpException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = "Something went wrong",
                )
            )
        } catch (e: IOException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = "Couldn't reach server, check your internet connection.",
                )
            )
        } catch (e: Exception) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = e.message.toString(),
                )
            )
        }
    }

    override fun getNowPlaying(): Flow<Resource<List<PresentationModel>>> = flow {
        emit(Resource.Loading<List<PresentationModel>>())
        try {
            val res = api.getNowPlaying().results.map { it.toPresentationModel(MOVIE) }
            emit(Resource.Success<List<PresentationModel>>(data = res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = "Something went wrong",
                )
            )
        } catch (e: IOException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = "Couldn't reach server, check your internet connection.",
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = e.message.toString(),
                )
            )
        }
    }

    override fun getPopularity(type: String): Flow<Resource<List<PresentationModel>>> = flow {
        emit(Resource.Loading<List<PresentationModel>>())
        try {
            val res = api.getPopularity(type).results.map { it.toPresentationModel(type) }
            emit(Resource.Success<List<PresentationModel>>(data = res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = "Something went wrong",
                )
            )
        } catch (e: IOException) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = "Couldn't reach server, check your internet connection.",
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<List<PresentationModel>>(
                    message = e.message.toString(),
                )
            )
        }
    }
}