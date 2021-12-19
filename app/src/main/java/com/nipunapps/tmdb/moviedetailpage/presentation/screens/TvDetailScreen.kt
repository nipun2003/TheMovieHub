package com.nipunapps.tmdb.moviedetailpage.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import com.nipunapps.tmdb.moviedetailpage.domain.model.TVDetailModel
import com.nipunapps.tmdb.moviedetailpage.presentation.components.OverView
import com.nipunapps.tmdb.moviedetailpage.presentation.components.tv.ProductionCompany
import com.nipunapps.tmdb.moviedetailpage.presentation.components.tv.TopBilledCast
import com.nipunapps.tmdb.moviedetailpage.presentation.viewmodels.TvDetailViewModel
import com.nipunapps.tmdb.ui.Error
import com.nipunapps.tmdb.ui.Screen
import com.nipunapps.tmdb.ui.theme.*

@Composable
fun TvDetailScreen(
    navController: NavController,
    tvId: Int,
    viewModel: TvDetailViewModel = hiltViewModel(),
    showBackground: (Boolean) -> Unit
) {
    val tvDetailState = viewModel.tvDetailState.value
    Box(modifier = Modifier.fillMaxSize()) {
        val listState = rememberLazyListState()
        val showButton by remember {
            mutableStateOf(
                derivedStateOf {
                    listState.firstVisibleItemIndex > 0
                })
        }
        showBackground(showButton.value)
        tvDetailState.data?.let { tv ->
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(ExtraBigPadding)
                            .background(ToolbarColor)
                    ) {
                    }
                }
                item {
                    Header(
                        backDrop = tv.backdrop_path ?: tv.poster_path ?: "error",
                        desc = tv.name,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(SmallPadding))
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = SmallPadding),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    ) {
                        Poster(posterPath = tv.poster_path ?: "error", desc = tv.name)
                        Spacer(modifier = Modifier.size(SmallPadding))
                        Detail(modifier = Modifier.fillMaxWidth(), tv = tv)
                    }
                }
                tv.overview?.let { overView ->
                    if (overView != "") {
                        item {
                            OverView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = SmallPadding, vertical = BigPadding),
                                overView = overView
                            )
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
                        casts = tv.casts
                    )
                    if (tv.production_companies.isNotEmpty()) {
                        ProductionCompany(
                            productionCompany = tv.production_companies,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = BigPadding)
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.size(PaddingStatusBar))
                }
            }
        }
        if (tvDetailState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        tvDetailState.message?.let { error ->
            Error(
                modifier = Modifier.fillMaxSize(),
                message = error,
            ) {
                navController.navigate(Screen.MovieDetailScreen.route + "/tv/$tvId") {
                    popUpTo(Screen.HomeScreen.route)
                }
            }
        }
    }
}

@Composable
fun Detail(
    modifier: Modifier = Modifier,
    tv: TVDetailModel
) {
    Column(modifier = modifier) {
        Text(
            text = tv.name,
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        if (tv.tagline != null && tv.tagline != "") {
            tv.tagline?.let { tagline ->
                Spacer(modifier = Modifier.size(ExtraSmallPadding))
                Text(
                    text = tagline,
                    style = MaterialTheme.typography.h3
                )
            }
        }
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        EpisodeInfo(header = "Seasons", number = tv.number_of_seasons)
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        EpisodeInfo(header = "Episodes", number = tv.number_of_episodes)
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        FlowRow(modifier = Modifier.fillMaxWidth()) {
            tv.genres.forEachIndexed { index, genre ->
                if (index == tv.genres.size - 1) {
                    Genre(
                        genre = genre.name,
                        isDot = false
                    )
                } else
                    Genre(genre = genre.name)
            }
        }
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        Rating(rating = tv.vote_average)
    }
}

@Composable
fun EpisodeInfo(
    header: String,
    number: Int
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        Text(
            text = header,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Normal
        )
    }
}