package com.nipunapps.tmdb.homepage.data.remote.dto.upcoming

import android.util.Log
import com.nipunapps.tmdb.core.Constants.MOVIE
import com.nipunapps.tmdb.core.checkReleaseDates
import com.nipunapps.tmdb.core.isHindiOrEnglish
import com.nipunapps.tmdb.homepage.domain.models.PresentationModel

data class UpcomingDto(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
) {
    fun toNowPlayingModel(): PresentationModel {
        var res = ArrayList<Result>()
        results.forEach { result ->
            if (result.original_language.isHindiOrEnglish() && (checkReleaseDates(result.release_date) || result.vote_average >= 5.5)) {
                Log.e("Result","now play -> ${result.title}")
                res.add(result)
            }
        }
        return res[(res.indices).random()].toPresentationModel(MOVIE)

    }
}