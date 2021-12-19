package com.nipunapps.tmdb.feature_search.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.nipunapps.tmdb.R
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.feature_search.domain.model.SingleSearchResult
import com.nipunapps.tmdb.moviedetailpage.presentation.screens.Genre
import com.nipunapps.tmdb.ui.Error
import com.nipunapps.tmdb.ui.Screen
import com.nipunapps.tmdb.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchResultState = viewModel.searchResult.value
    val message = viewModel.searchResult.value.message
    val prevQueries = viewModel.prevQuery.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(ExtraBigPadding)
                    .background(ToolbarColor)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SmallPadding)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    val focusRequester = remember {
                        FocusRequester()
                    }
                    TextField(
                        value = viewModel.searchQuery.value,
                        onValueChange = {
                            if (it.isNotEmpty())
                                viewModel.onSearch(it)
                            else {
                                viewModel.cancelSearch()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                        placeholder = {
                            Text(text = "Search...")
                        },
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "close",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(SmallPadding)
                            .align(Alignment.CenterEnd)
                            .clickable {
                                viewModel.cancelSearch()
                                focusRequester.requestFocus()
                            }
                    )
                }
                PrevSearch(
                    modifier = Modifier.fillMaxWidth(),
                    list = prevQueries
                ) {
                    viewModel.onSearch(it)
                }
                Spacer(modifier = Modifier.size(BigPadding))
                if (searchResultState.data.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(searchResultState.data.size) { i ->
                            if (i > 0) {
                                Spacer(modifier = Modifier.size(SmallPadding))
                            }
                            SearchResultCard(
                                res = searchResultState.data[i],
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(SmallPadding)
                            ) { type, id ->
                                navController.navigate(Screen.MovieDetailScreen.route + "/$type/$id")
                            }
                        }
                    }
                }

            }
        }
        if (searchResultState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        if (message != null && message != "") {
            Error(
                modifier = Modifier.fillMaxSize(),
                message = message,
            ) {
                viewModel.onSearch(viewModel.searchQuery.value)
            }
        }
    }


}

@Composable
fun SearchResultCard(
    modifier: Modifier = Modifier,
    res: SingleSearchResult,
    onclick: (String, Int) -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(ExtraSmallPadding))
            .clickable {
                onclick(res.media_type, res.id)
            },
        elevation = ExtraSmallPadding,
        backgroundColor = ToolbarDarkVariant
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = rememberImagePainter(
                    data = when (res.media_type) {
                        Constants.PERSON -> "${Constants.IMAGE_PATH}${res.poster_path}"
                        else -> "${Constants.POSTER_PATH}${res.poster_path}"
                    }
                ),
                contentDescription = res.name,
                modifier = Modifier
                    .width(100.dp)
                    .aspectRatio(0.55f),
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SmallPadding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = res.name,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.size(ExtraSmallPadding))
                Text(
                    text = res.releaseDate,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.size(ExtraSmallPadding))
                Text(
                    text = res.overview,
                    style = MaterialTheme.typography.body2,
                    maxLines = 4
                )
            }
        }
    }
}

@Composable
fun PrevSearch(
    modifier: Modifier = Modifier,
    list: List<String>,
    onclick: (String) -> Unit
) {
    val queries = list.reversed()
    Row(
        modifier = modifier
            .padding(top = SmallPadding, start = SmallPadding)
            .clip(RoundedCornerShape(ExtraSmallPadding))
            .padding(SmallPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Previous Search",
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(end = BigPadding)
        ) {
            items(queries.size) {
                if (it < 3) {
                    if (it > 0) {
                        Spacer(modifier = Modifier.size(ExtraSmallPadding))
                    }
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(ExtraSmallPadding))
                            .border(1.dp, color = Color.Green)
                            .padding(SmallPadding)
                            .clickable {
                                onclick(queries[it])
                            }
                    ) {
                        Text(
                            text = queries[it],
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
