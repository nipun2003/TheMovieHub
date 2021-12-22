package com.nipunapps.tmdb.homepage.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.homepage.domain.models.PresentationModel
import com.nipunapps.tmdb.ui.theme.BigPadding
import com.nipunapps.tmdb.ui.theme.ExtraBigPadding
import com.nipunapps.tmdb.ui.theme.SmallPadding
import kotlinx.coroutines.launch

@Composable
fun Trending(
    modifier: Modifier = Modifier,
    header: String,
    movies: List<PresentationModel>,
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
fun PresentationDropDown(
    modifier: Modifier = Modifier,
    header: String,
    dropDown: List<String>,
    movies: List<PresentationModel>,
    selectedIndex: Int,
    onDropDownClick: (String, Int) -> Unit,
    onClick: (String, Int) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        val listState = rememberLazyListState()
        // Remember a CoroutineScope to be able to launch
        val coroutineScope = rememberCoroutineScope()
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = BigPadding),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = header,
                style = MaterialTheme.typography.h3,
                color = Color.White,
                modifier = Modifier
                    .padding(end = SmallPadding)
                    .clickable {
                        coroutineScope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    }
            )
            ComposeMenu(
                menuItems = dropDown,
                menuExpandedState = menuExpanded,
                selectedIndex = selectedIndex,
                updateMenuExpandStatus = {
                    menuExpanded = true
                },
                onDismissMenuView = {
                    menuExpanded = false
                },
                onMenuItemClick = {
                    onDropDownClick(dropDown[it], it)
                    menuExpanded = false
                }
            )
        }
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
    movie: PresentationModel,
    onClick: (String, Int) -> Unit
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(150.dp)
            .clickable {
                onClick(movie.mediaType, movie.id)
            }
    ) {
        Image(
            painter = rememberImagePainter(
                data = "${Constants.POSTER_PATH}${movie.logoPath}"
            ),
            contentDescription = movie.title,
            modifier = Modifier.clip(RoundedCornerShape(SmallPadding))
        )
    }
}

@Composable
fun ComposeMenu(
    menuItems: List<String>,
    menuExpandedState: Boolean,
    selectedIndex: Int,
    updateMenuExpandStatus: () -> Unit,
    onDismissMenuView: () -> Unit,
    onMenuItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                }
            )

    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (selectedIndex) {
                    0 -> "Movies"
                    1 -> "Tv Shows"
                    else -> ""
                },
                style = MaterialTheme.typography.h3,
            )
            Spacer(modifier = Modifier.size(SmallPadding))
            Icon(
                painter = rememberVectorPainter(image = Icons.Default.ArrowDropDown),
                contentDescription = "dropDown",
                Modifier.size(ExtraBigPadding)
            )
        }
        DropdownMenu(
            expanded = menuExpandedState,
            onDismissRequest = { onDismissMenuView() },
            modifier = modifier
                .background(MaterialTheme.colors.background)
        ) {
            menuItems.forEachIndexed { index, title ->
                DropdownMenuItem(
                    onClick = {
                        if (index >= 0) {
                            onMenuItemClick(index)
                        }
                    }) {
                    Text(text = title)
                }
            }
        }
    }
}

