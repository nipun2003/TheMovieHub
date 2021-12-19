package com.nipunapps.tmdb

import android.app.Application
import androidx.room.Room
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.feature_search.data.local.QueryDatabase
import com.nipunapps.tmdb.feature_search.data.local.QueryInfoDao
import com.nipunapps.tmdb.feature_search.data.repository.QueryResultRepositoryImpl
import com.nipunapps.tmdb.feature_search.domain.repository.QueryResultRepository
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

    @Singleton
    @Provides
    fun getQueryResultRepository(api : HomeApi,dao: QueryInfoDao) : QueryResultRepository {
        return QueryResultRepositoryImpl(api,dao)
    }

    @Singleton
    @Provides
    fun provideQueryDao(db : QueryDatabase) : QueryInfoDao{
        return db.dao
    }

    @Singleton
    @Provides
    fun provideDatabase(app : Application) : QueryDatabase{
        return Room.databaseBuilder(
            app,QueryDatabase::class.java,"query_db"
        ).build()
    }
}