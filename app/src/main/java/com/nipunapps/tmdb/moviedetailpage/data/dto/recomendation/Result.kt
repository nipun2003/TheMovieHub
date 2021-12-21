package com.nipunapps.tmdb.moviedetailpage.data.dto.recomendation

import com.nipunapps.tmdb.moviedetailpage.domain.model.RecommendModel

data class Result(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val id: Int,
    val media_type: String,
    val name : String?,
    val original_language: String?,
    val original_title: String,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)
{
    fun toRecommendModel(): RecommendModel {
        return RecommendModel(
            id = id,
            mediaType = media_type,
            logoPath = backdrop_path?:poster_path?:"",
            name = name?:title?:""
        )
    }
}