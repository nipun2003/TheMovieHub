package com.nipunapps.tmdb.homepage.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.homepage.data.remote.dto.trending.Result
import com.nipunapps.tmdb.ui.theme.BigPadding
import com.nipunapps.tmdb.ui.theme.SmallPadding
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun Trending(
    modifier: Modifier = Modifier,
    header: String,
    movies: List<Result>,
    onClick: (String, Int) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        val listState = rememberLazyListState()
        // Remember a CoroutineScope to be able to launch
        val coroutineScope = rememberCoroutineScope()
        Text(
            text = header,
            style = MaterialTheme.typography.h3,
            color = Color.White,
            modifier = Modifier
                .padding(start = BigPadding)
                .clickable {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            contentPadding = PaddingValues(start = BigPadding)
        ) {
            items(movies.size) { item ->
                PosterCard(movie = movies[item]) { type, id ->
                    onClick(type, id)
                }
            }
        }
    }
}

@Composable
fun PosterCard(
    movie: Result,
    onClick: (String, Int) -> Unit
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(150.dp)
            .clickable {
                onClick(movie.media_type, movie.id)
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