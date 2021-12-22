package com.nipunapps.tmdb.homepage.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.core.InfoIcon
import com.nipunapps.tmdb.core.Plus
import com.nipunapps.tmdb.homepage.presentation.components.PresentationDropDown
import com.nipunapps.tmdb.homepage.presentation.components.Trending
import com.nipunapps.tmdb.moviedetailpage.domain.model.MovieDetailModel
import com.nipunapps.tmdb.ui.Error
import com.nipunapps.tmdb.ui.Screen
import com.nipunapps.tmdb.ui.theme.*

@ExperimentalPagerApi
@Composable
fun Homepage(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    showBackground: (Boolean) -> Unit
) {


    val upcomingMoviesState = viewModel.upcomingMovies
    val upComingMovies = viewModel.upcomingMovies.value.data
    val trendingMovies = viewModel.trendingMovies.value.data
    val posterMovie = viewModel.movieDetailState.value.data
    val message = viewModel.movieDetailState.value.message
    val nowPlaying = viewModel.nowPlaying.value.data
    val popular = viewModel.popular.value.data

    val collection = listOf(
        PresentationCollection(
            header = "What's Trending",
            data = trendingMovies
        ),
        PresentationCollection(
            header = "Now Playing",
            data = nowPlaying
        ),
        PresentationCollection(
            header = "Popular",
            data = popular,
            isDropDown = listOf(Constants.MOVIE, Constants.TV)
        ),
        PresentationCollection(
            header = "Released this year",
            data = upComingMovies
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val listState = rememberLazyListState()
        val showButton by remember {
            mutableStateOf(
                derivedStateOf {
                    listState.firstVisibleItemIndex > 0
                })
        }
        showBackground(showButton.value)
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                if (posterMovie != null) {
                    posterMovie?.let { nowPlayMovie ->
                        SingleSlider(
                            movie = nowPlayMovie,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(450.dp)
                        ) { id ->
                            navController.navigate(Screen.MovieDetailScreen.route + "/movie/$id")
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.size(60.dp))
                }
            }
            item {
                Spacer(modifier = Modifier.size(BigPadding))
            }
            items(collection.size) {
                val presentation = collection[it]
                if (it > 0) {
                    Spacer(modifier = Modifier.size(SmallPadding))
                }
                if (presentation.data.isNotEmpty()) {
                    if (presentation.isDropDown.isNotEmpty()) {
                        PresentationDropDown(
                            header = presentation.header,
                            dropDown = presentation.isDropDown,
                            movies = presentation.data,
                            selectedIndex = viewModel.popularitySelected.value,
                            onDropDownClick = { type, selected ->
                                viewModel.getPopularity(type,selected)
                            }
                        ) { type, id ->
                            navController.navigate(Screen.MovieDetailScreen.route + "/$type/$id")
                        }
                    } else {
                        Trending(
                            header = presentation.header,
                            movies = presentation.data,
                            modifier = Modifier.fillMaxSize()
                        ) { type, id ->
                            navController.navigate(Screen.MovieDetailScreen.route + "/$type/$id")
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.size(ExtraBigPadding))
            }
        }
        if (upcomingMoviesState.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (message != null && message != "") {

            Error(
                modifier = Modifier.fillMaxSize(),
                message = message,
            ) {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.HomeScreen.route) { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun SingleSlider(
    modifier: Modifier = Modifier,
    movie: MovieDetailModel,
    onClick: (Int) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = rememberImagePainter(
                data = "${Constants.POSTER_PATH}${movie.backDropPath}"
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp),
            contentDescription = movie.title,
            contentScale = ContentScale.FillHeight,
            alignment = Alignment.Center,
            alpha = 0.65f
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                SemiToolbar,
                                ToolbarColor
                            ),
                        )
                    )
                    .padding(SmallPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(SmallPadding))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisAlignment = MainAxisAlignment.Center
                ) {
                    movie.genres.forEachIndexed { index, genre ->
                        if (index == movie.genres.size - 1) {
                            MainGenre(
                                genre = genre.name,
                                isDot = false
                            )
                        } else
                            MainGenre(genre = genre.name)
                    }
                }
                Spacer(modifier = Modifier.size(SmallPadding))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(SmallPadding)
                    ) {
                        Plus()
                        Spacer(modifier = Modifier.size(ExtraSmallPadding))
                        Text(
                            text = "My List",
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(SmallPadding)
                            .clickable {
                                onClick(movie.id)
                            }
                    ) {
                        InfoIcon()
                        Spacer(modifier = Modifier.size(ExtraSmallPadding))
                        Text(
                            text = "Info",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainGenre(
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
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Start
        )
        if (isDot) {
            Spacer(modifier = Modifier.size(ExtraSmallPadding))
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(RoundedCornerShape(ExtraSmallPadding))
                    .background(color = Color.Gray)
            )
        }
    }
}

