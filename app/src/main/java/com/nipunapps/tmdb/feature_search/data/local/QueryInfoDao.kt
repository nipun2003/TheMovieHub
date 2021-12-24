package com.nipunapps.tmdb.feature_search.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nipunapps.tmdb.feature_search.data.local.entity.QueryResultEntity

@Dao
interface QueryInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuery(query:QueryResultEntity)

    @Query("SELECT * from queryresultentity")
    suspend fun getPreviousSearch() : List<QueryResultEntity>

    @Query("SELECT * from queryresultentity where `query` like '%' || :search || '%'")
    suspend fun getRelatedQuery(search:String) : List<QueryResultEntity>
}