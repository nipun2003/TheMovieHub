package com.nipunapps.tmdb.feature_search.data.remote.dto

import com.nipunapps.tmdb.feature_search.domain.model.SingleSearchResult

data class Result(
    val adult: Boolean,
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<Int>?,
    val id: Int,
    val media_type: String,
    val name: String?,
    val origin_country: List<String>?,
    val original_language: String?,
    val original_name: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val profile_path : String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
){
    fun toSingleSearchResult() : SingleSearchResult{
        return SingleSearchResult(
            adult = adult,
            releaseDate = first_air_date?:release_date?:"",
            genre_ids = genre_ids?: emptyList(),
            id = id,
            name = name?:title?:original_name?:original_title?:"",
            media_type = media_type,
            original_title = original_name?:original_title?:"",
            overview = overview?:"",
            poster_path = poster_path?:profile_path?:""
        )
    }
}