package com.nipunapps.tmdb.core

import android.os.Environment
import java.io.File

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val AUTH_API = "b05ff04b7d21f0487de83fdd89276cf9"
    const val POSTER_PATH = "https://www.themoviedb.org/t/p/original/"
    const val POSTER_Logo = "https://www.themoviedb.org/t/p/original"
    const val IMAGE_PATH = "https://image.tmdb.org/t/p/w500/"
    const val LOGO_PATH = "https://image.tmdb.org/t/p/w500"

    //Trending Constants
    const val ALL = "all"
    const val MOVIE = "movie"
    const val TV = "tv"
    const val PERSON = "person"

    // Time_window constants
    const val DAY = "day"
    const val WEEK = "week"

    val ROOT_PATH =
        File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath}/TMDB")
}