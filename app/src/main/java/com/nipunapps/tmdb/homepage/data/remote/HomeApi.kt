package com.nipunapps.tmdb.homepage.data.remote

import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.core.Constants.AUTH_API
import com.nipunapps.tmdb.feature_search.data.remote.dto.QueryResultDto
import com.nipunapps.tmdb.homepage.data.remote.dto.trending.TrendingMovieDto
import com.nipunapps.tmdb.homepage.data.remote.dto.upcoming.UpcomingDto
import com.nipunapps.tmdb.moviedetailpage.data.dto.movie_detail.MovieDetailDto
import com.nipunapps.tmdb.moviedetailpage.data.dto.tv_detail.TVDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("movie/{movie_id}?api_key=$AUTH_API&append_to_response=videos,credits,images&region=IN")
    suspend fun getMovieDetails(
        @Path(value = "movie_id") movieId: Int?
    ): MovieDetailDto

    @GET("tv/{tv_id}?api_key=$AUTH_API&append_to_response=videos,credits,images&region=IN")
    suspend fun getTvDetails(
        @Path(value = "tv_id") tvId : Int?
    ) : TVDetailDto

    @GET("search/multi?api_key=${Constants.AUTH_API}&include_adult=true")
    suspend fun searchAll(@Query(value = "query") query : String?) : QueryResultDto

}