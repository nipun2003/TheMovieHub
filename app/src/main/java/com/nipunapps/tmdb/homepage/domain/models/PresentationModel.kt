package com.nipunapps.tmdb.homepage.domain.models

import okhttp3.MediaType

data class PresentationModel(
    val id : Int,
    val mediaType: String,
    val logoPath : String,
    val title : String,
    val popularity: Double = 0.0
)
