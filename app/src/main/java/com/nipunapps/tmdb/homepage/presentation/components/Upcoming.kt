package com.nipunapps.tmdb.homepage.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.homepage.data.remote.dto.upcoming.Result
import com.nipunapps.tmdb.ui.theme.BigPadding
import com.nipunapps.tmdb.ui.theme.SmallPadding

@ExperimentalPagerApi
@Composable
fun Upcoming(
    modifier: Modifier = Modifier,
    header: String,
    movies: List<Result>,
    onClick : (Int) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.h3,
            color = Color.White,
            modifier = Modifier.padding(start = BigPadding)
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            contentPadding = PaddingValues(start = BigPadding)
        ) {
            items(movies.size) { item ->
                PosterCard(movie = movies[item]){
                    onClick(it)
                }
            }
        }
    }
}

@Composable
fun PosterCard(
    movie: Result,
    onClick : (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(150.dp)
            .clickable {
                onClick(movie.id)
            }
    ) {
        Image(
            painter = rememberImagePainter(
                data = "${Constants.POSTER_PATH}${movie.poster_path}"
            ),
            contentDescription = movie.title,
            modifier = Modifier.clip(RoundedCornerShape(SmallPadding))
        )
    }
}