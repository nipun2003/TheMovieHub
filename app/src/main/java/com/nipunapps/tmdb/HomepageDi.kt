package com.nipunapps.tmdb

import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.homepage.data.remote.HomeApi
import com.nipunapps.tmdb.homepage.data.repository.MoviesApiImpl
import com.nipunapps.tmdb.homepage.domain.repository.MoviesApi
import com.nipunapps.tmdb.moviedetailpage.data.repository.MovieDetailRepositoryImpl
import com.nipunapps.tmdb.moviedetailpage.domain.repository.MovieDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomepageDi {

    @Provides
    @Singleton
    fun provideMovieApi(api : HomeApi) : MoviesApi = MoviesApiImpl(api)

    @Provides
    @Singleton
    fun provideMovieDetailRepository(api : HomeApi) : MovieDetailRepository = MovieDetailRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideHomeApi() : HomeApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeApi::class.java)
    }
}