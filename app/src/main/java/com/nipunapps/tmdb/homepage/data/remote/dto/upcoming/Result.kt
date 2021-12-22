package com.nipunapps.tmdb.homepage.data.remote.dto.upcoming

import com.nipunapps.tmdb.core.Constants.MOVIE
import com.nipunapps.tmdb.core.toTime
import com.nipunapps.tmdb.homepage.domain.models.PresentationModel

data class Result(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val media_type: String?,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val name : String?
){
    fun toPresentationModel(type: String = MOVIE):PresentationModel{
        return PresentationModel(
            id = id,
            mediaType = media_type?:type,
            logoPath = poster_path?:backdrop_path?:"",
            title = title?:"",
            popularity = popularity
        )
    }
}