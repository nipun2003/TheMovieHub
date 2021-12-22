package com.nipunapps.tmdb.homepage.presentation

import com.nipunapps.tmdb.homepage.domain.models.PresentationModel

data class PresentationCollection(
    val header : String,
    val data : List<PresentationModel>,
    val isDropDown : List<String> = emptyList()
)
