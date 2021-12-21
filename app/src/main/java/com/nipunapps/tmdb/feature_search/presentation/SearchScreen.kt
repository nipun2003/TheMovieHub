package com.nipunapps.tmdb.feature_search.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.nipunapps.tmdb.R
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.feature_search.domain.model.SingleSearchResult
import com.nipunapps.tmdb.ui.Error
import com.nipunapps.tmdb.ui.Screen
import com.nipunapps.tmdb.ui.theme.*

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchResultState = viewModel.searchResult.value
    val message = viewModel.searchResult.value.message
    val isRecent = viewModel.searchResult.value.isRecent
    val prevQuery = viewModel.prevQuery.value.reversed()
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
                SearchBox(viewModel = viewModel)
                Spacer(modifier = Modifier.size(BigPadding))
                if (searchResultState.data.isNotEmpty()) {
                    if (isRecent) {
                        Text(
                            text = "Recent Search : ${prevQuery[0]}",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(start = SmallPadding)
                        )
                    }
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
        if (searchResultState.isFromSuccess && searchResultState.data.isEmpty()) {
            Text(
                text = "No Result Found",
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h3
            )
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
fun SearchBox(
    viewModel: SearchViewModel,
) {
    val focusManager = LocalFocusManager.current
    val prevQueries = viewModel.prevQuery.value.reversed()
    val focusRequester = remember {
        FocusRequester()
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = SmallPadding,
        color = ToolbarColor,
        border = BorderStroke(
            width = 1.5.dp,
            color = ToolbarComplement
        ),
        shape = RoundedCornerShape(SmallPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            var chipsVisibility by remember {
                mutableStateOf(false)
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = viewModel.searchQuery.value,
                    onValueChange = viewModel::updateQuery,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            chipsVisibility = it.hasFocus
                        },
                    placeholder = {
                        Text(
                            text = "Search for a show, movie, people etc.",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.W100
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = CursorColor
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = true,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        viewModel.onSearch(viewModel.searchQuery.value)
                        focusManager.clearFocus(force = true)
                    }),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search",
                            modifier = Modifier.size(ExtraBigPadding)
                        )
                    },
                    textStyle = MaterialTheme.typography.h3,
                )
                if (viewModel.closeIconState.value) {
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
            }
            AnimatedVisibility(visible = chipsVisibility) {
                PrevSearch(
                    modifier = Modifier
                        .fillMaxWidth(),
                    queries = prevQueries
                ) {
                    viewModel.onSearch(it)
                    focusManager.clearFocus(force = true)
                }
            }
        }
    }
}

@Composable
fun PrevSearch(
    modifier: Modifier = Modifier,
    queries: List<String>,
    onclick: (String) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(SmallPadding)
    ) {
        items(queries.size) {
            if (it < 5) {
                if (it > 0) {
                    Spacer(modifier = Modifier.size(ExtraSmallPadding))
                }
                Surface(
                    modifier = Modifier
                        .clickable {
                            onclick(queries[it])
                        },
                    contentColor = Color.White,
                    color = Color.Transparent,
                    border = BorderStroke(
                        width = 1.dp,
                        color = ChipBorder
                    ),
                    shape = RoundedCornerShape(SmallPadding)
                ) {
                    Text(
                        text = queries[it],
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(SmallPadding)
                    )
                }
            }
        }
    }
}
