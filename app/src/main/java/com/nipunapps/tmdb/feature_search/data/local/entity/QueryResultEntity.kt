package com.nipunapps.tmdb.feature_search.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nipunapps.tmdb.feature_search.data.remote.dto.Result

@Entity
data class QueryResultEntity(
    @PrimaryKey val query : String
)