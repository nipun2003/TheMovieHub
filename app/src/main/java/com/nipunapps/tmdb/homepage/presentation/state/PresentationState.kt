package com.nipunapps.tmdb.homepage.presentation.state

import com.nipunapps.tmdb.homepage.domain.models.PresentationModel

data class PresentationState(
    val isLoading : Boolean = false,
    val data :List<PresentationModel> = emptyList(),
    val message : String = ""
)