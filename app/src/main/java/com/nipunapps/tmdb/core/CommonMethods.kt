package com.nipunapps.tmdb.core

import java.sql.Timestamp
import java.util.regex.Pattern

fun checkReleaseDates(releaseDate : String?) : Boolean{
    if(releaseDate == null) return true
    val relDate = "$releaseDate 00:00:00"
    val relDateInSec = (Timestamp.valueOf(relDate).time)/1000L
    val currentTimeInSec =  (Timestamp(System.currentTimeMillis()).time)/1000L
    val diff : Long = 86400*3
    if((currentTimeInSec - relDateInSec) <= diff) return true
    return false
}

fun String.toTime():Long{
    val relDate = "$this 00:00:00"
    return (Timestamp.valueOf(relDate).time)/1000L
}

fun String.isValidName():Boolean{
    return this.filter { it in 'A'..'Z' || it in 'a'..'z' || it in '0'..'9' || isSpecialChar(it) }.length == this.length
}

fun String.isHindiOrEnglish() : Boolean{
    return (this == "en") || (this == "hi")
}

private fun isSpecialChar(c : Char) : Boolean{
    val p: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
    val matcher = p.matcher(c.toString())
    return matcher.find()
}

fun Int.toTime():String{
    val hour = this/60;
    val minute = this%60;
    return if(hour>0) "${hour}h ${minute}m"
    else "${minute}m"
}