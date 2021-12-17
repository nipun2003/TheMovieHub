package com.nipunapps.tmdb.moviedetailpage.data.repository

import android.util.Log
import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.data.remote.HomeApi
import com.nipunapps.tmdb.homepage.domain.models.UpcomingModel
import com.nipunapps.tmdb.moviedetailpage.domain.model.MovieDetailModel
import com.nipunapps.tmdb.moviedetailpage.domain.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieDetailRepositoryImpl(
    private val api : HomeApi
) :MovieDetailRepository{

    override fun getMovieDetail(id: Int): Flow<Resource<MovieDetailModel>> = flow {
        emit(Resource.Loading<MovieDetailModel>())
        Log.e("movieId",id.toString())
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
}