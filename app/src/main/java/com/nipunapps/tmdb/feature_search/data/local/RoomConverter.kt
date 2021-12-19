package com.nipunapps.tmdb.feature_search.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.nipunapps.tmdb.feature_search.data.remote.dto.Result
import com.nipunapps.tmdb.feature_search.util.JsonParser

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromMeaningsJson(json: String): List<Result> {
        return jsonParser.fromJson<ArrayList<Result>>(
            json,
            object : TypeToken<ArrayList<Result>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningsJson(meanings: List<Result>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Result>>(){}.type
        ) ?: "[]"
    }
}