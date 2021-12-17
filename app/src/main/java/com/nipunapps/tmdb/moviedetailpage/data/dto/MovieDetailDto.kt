package com.nipunapps.tmdb.moviedetailpage.data.dto

import com.nipunapps.tmdb.moviedetailpage.domain.model.MovieDetailModel

data class MovieDetailDto(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: BelongsToCollection?,
    val budget: Int,
    val credits: Credits,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>?,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val videos: Videos,
    val vote_average: Double,
    val vote_count: Int
){
    fun toMovieDetailModel() : MovieDetailModel{
        return MovieDetailModel(
            adult = adult,
            backDropPath = backdrop_path,
            belongsToCollection = belongs_to_collection,
            budget = budget,
            casts = credits.cast.sortedBy { it.order },
            genres = genres,
            homepage = homepage,
            id = id,
            originalLanguage = original_language,
            originalTitle = original_title,
            overview = overview,
            popularity = popularity,
            poster_path = poster_path,
            productionCompanies = production_companies,
            productionCountries = production_countries,
            release_date = release_date,
            revenue = revenue,
            runtime = runtime,
            status = status,
            tagline = tagline,
            title = title,
            video = video,
            videos = videos,
            vote_average = vote_average
        )
    }
}