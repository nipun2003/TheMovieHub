package com.nipunapps.tmdb.moviedetailpage.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.nipunapps.tmdb.ui.theme.BigPadding
import com.nipunapps.tmdb.ui.theme.SmallPadding

@Composable
fun OverView(
    modifier: Modifier = Modifier,
    overView : String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "OverView",
            style = MaterialTheme.typography.h3,
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        Text(
            text = overView,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Start
        )
    }
}