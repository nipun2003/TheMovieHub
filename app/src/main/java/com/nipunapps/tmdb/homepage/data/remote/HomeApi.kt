package com.nipunapps.tmdb.homepage.data.remote

import com.google.gson.JsonObject
import com.nipunapps.tmdb.core.Constants.AUTH_API
import com.nipunapps.tmdb.core.Constants.BASE_URL
import com.nipunapps.tmdb.homepage.data.remote.dto.trending.TrendingMovieDto
import com.nipunapps.tmdb.homepage.data.remote.dto.upcoming.UpcomingDto
import com.nipunapps.tmdb.moviedetailpage.data.dto.MovieDetailDto
import okhttp3.MediaType
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {

    @GET("movie/upcoming?api_key=$AUTH_API")
    suspend fun getUpcoming(): UpcomingDto

    @GET("trending/{mediaType}/{time_window}?api_key=$AUTH_API")
    suspend fun getTrending(
        @Path(value = "mediaType") mediaType: String,
        @Path(value = "time_window") time: String
    ): TrendingMovieDto

    @GET("movie/now_playing?api_key=$AUTH_API&language=hi&region=IN")
    suspend fun getNowPlaying(): UpcomingDto

    @GET("movie/{movie_id}?api_key=$AUTH_API&append_to_response=videos,credits&region=IN")
    suspend fun getMovieDetails(
        @Path(value = "movie_id") movieId: Int?
    ): MovieDetailDto

}