package com.nipunapps.tmdb.moviedetailpage.domain.model

import com.nipunapps.tmdb.moviedetailpage.data.dto.*

data class MovieDetailModel(
    val adult: Boolean,
    val backDropPath: String?,
    val belongsToCollection: BelongsToCollection?,
    val budget: Int,
    val casts: List<Cast>,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val videos: Videos,
    val vote_average: Double,
)
