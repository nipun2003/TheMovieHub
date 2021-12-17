package com.nipunapps.tmdb.moviedetailpage.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.core.toTime
import com.nipunapps.tmdb.moviedetailpage.domain.model.MovieDetailModel
import com.nipunapps.tmdb.moviedetailpage.presentation.components.TopBilledCast
import com.nipunapps.tmdb.ui.Error
import com.nipunapps.tmdb.ui.Screen
import com.nipunapps.tmdb.ui.theme.*

@Composable
fun MovieDetailScreen(
    navController: NavController,
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movieDetailState = viewModel.movieDetailState.value
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        movieDetailState.data?.let { movie ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    Header(
                        backDrop = movie.backDropPath?:movie.poster_path?:"error",
                        desc = movie.title,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.size(SmallPadding))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = SmallPadding),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    ) {
                        Poster(posterPath = movie.poster_path ?: "error", desc = movie.title)
                        Spacer(modifier = Modifier.size(SmallPadding))
                        Detail(modifier = Modifier.fillMaxWidth(), movie = movie)
                    }
                }
                movie.overview?.let { overView ->
                    if (overView != "") {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = SmallPadding, vertical = BigPadding)
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
                    }
                }

                item {
                    HorizontalLine()
                }
                item {
                    TopBilledCast(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = BigPadding),
                        casts = movie.casts
                    )
                }
            }
        }
        if (movieDetailState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        movieDetailState.message?.let { error ->
            Error(
                modifier = Modifier.fillMaxSize(),
                message = error,
            ) {
                navController.navigate(Screen.MovieDetailScreen.route + "/movie/$movieId") {
                    popUpTo(Screen.HomeScreen.route)
                }
            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    backDrop: String,
    desc: String
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = rememberImagePainter(
                data = "${Constants.POSTER_PATH}$backDrop"
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp),
            contentDescription = desc,
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.Center,
            alpha = 0.6f
        )
    }
}

@Composable
fun Poster(
    modifier: Modifier = Modifier,
    posterPath: String,
    desc: String

) {
    Box(modifier = modifier) {
        Image(
            painter = rememberImagePainter(
                data = "${Constants.POSTER_PATH}$posterPath"
            ),
            modifier = Modifier
                .width(120.dp)
                .aspectRatio(0.65f)
                .clip(RoundedCornerShape(SmallPadding)),
            contentDescription = desc,
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.Center
        )
    }
}

@Composable
fun Detail(
    modifier: Modifier = Modifier,
    movie: MovieDetailModel
) {
    Column(modifier = modifier) {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        if (movie.tagline != null && movie.tagline != "") {
            movie.tagline?.let { tagline ->
                Spacer(modifier = Modifier.size(ExtraSmallPadding))
                Text(
                    text = tagline,
                    style = MaterialTheme.typography.h3
                )
            }
        }
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        RunTime(
            runtime = movie.runtime ?: 0,
            release = movie.release_date,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        FlowRow(modifier = Modifier.fillMaxWidth()) {
            movie.genres.forEachIndexed { index, genre ->
                if (index == movie.genres.size - 1) {
                    Genre(
                        genre = genre.name,
                        isDot = false
                    )
                } else
                    Genre(genre = genre.name)
            }
        }
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        Rating(rating = movie.vote_average)
    }
}


@Composable
fun Genre(
    modifier: Modifier = Modifier,
    genre: String,
    isDot: Boolean = true
) {
    Row(
        modifier = Modifier.padding(end = ExtraSmallPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = genre,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Start
        )
        if (isDot) {
            Spacer(modifier = Modifier.size(ExtraSmallPadding))
            Box(
                modifier = Modifier
                    .size(2.dp)
                    .clip(RoundedCornerShape(ExtraSmallPadding))
                    .background(color = Color.Gray)
            )
        }
    }
}

@Composable
fun RunTime(
    modifier: Modifier = Modifier,
    runtime: Int,
    release: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = release.replace('-', '/'),
            style = MaterialTheme.typography.body2,
            color = Color.White,
            fontWeight = FontWeight.W800,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        Box(
            modifier = Modifier
                .size(3.dp)
                .clip(RoundedCornerShape(ExtraSmallPadding))
                .background(color = Color.Gray)
        )
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        Text(
            text = runtime.toTime(),
            style = MaterialTheme.typography.body2,
            color = Color.White,
            fontWeight = FontWeight.W800,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Rating(
    rating: Double
) {
    Text(
        text = "$rating+",
        style = MaterialTheme.typography.body2,
        color = if (rating >= 5.5) Color.Green else Color.Red
    )
}

@Composable
fun HorizontalLine() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = ExtraSmallPadding)
    ) {
        drawLine(
            brush = Brush.linearGradient(colors = DividerColor),
            start = Offset(0f, 0f),
            end = Offset(this.size.width * 1f, 0f),
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )
    }
}