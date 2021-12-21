package com.nipunapps.tmdb.moviedetailpage.data.dto.tv_detail

import com.nipunapps.tmdb.moviedetailpage.data.dto.movie_detail.Backdrop

data class Images(
    val backdrops: List<Backdrop>,
    val logos: List<Logo>,
    val posters: List<Poster>
)