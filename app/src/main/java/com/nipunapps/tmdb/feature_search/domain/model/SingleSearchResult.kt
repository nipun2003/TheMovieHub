package com.nipunapps.tmdb.feature_search.domain.model

data class SingleSearchResult(
    val adult: Boolean,
    val releaseDate: String,
    val genre_ids: List<Int> = emptyList(),
    val id: Int,
    val media_type: String,
    val name: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
)
