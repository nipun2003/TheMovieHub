package com.nipunapps.tmdb.feature_search.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nipunapps.tmdb.feature_search.data.local.entity.QueryResultEntity

@Database(
    entities = [QueryResultEntity::class],
    version = 1
)
abstract class QueryDatabase : RoomDatabase() {
    abstract val dao : QueryInfoDao
}