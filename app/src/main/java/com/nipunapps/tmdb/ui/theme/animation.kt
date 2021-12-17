package com.nipunapps.tmdb.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween

const val animationDuration = 350
fun enterAnimation(offset: Int): EnterTransition = slideInHorizontally(
    initialOffsetX = { offset },
    animationSpec = tween(
        durationMillis = animationDuration,
        easing = FastOutSlowInEasing
    )
) + fadeIn(animationSpec = tween(animationDuration))

fun exitAnimation(offset: Int) : ExitTransition = slideOutHorizontally(
    targetOffsetX = { offset },
    animationSpec = tween(
        durationMillis = animationDuration,
        easing = FastOutSlowInEasing
    )
) + fadeOut(animationSpec = tween(animationDuration))
