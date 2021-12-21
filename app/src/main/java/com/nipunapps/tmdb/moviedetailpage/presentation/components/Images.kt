package com.nipunapps.tmdb.moviedetailpage.presentation.components

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.moviedetailpage.data.dto.movie_detail.Backdrop
import com.nipunapps.tmdb.moviedetailpage.domain.model.RecommendModel
import com.nipunapps.tmdb.ui.theme.ExtraSmallPadding
import com.nipunapps.tmdb.ui.theme.SmallPadding

@Composable
fun FImage(
    list: List<Backdrop>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = SmallPadding),
            text = "Images",
            style = MaterialTheme.typography.h3,
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(SmallPadding)
        ) {
            items(list.size) {
                Image(
                    painter = rememberImagePainter(
                        data = "${Constants.POSTER_Logo}${list[it].file_path}"
                    ),
                    contentDescription = list[it].file_path,
                    modifier = Modifier
                        .padding(end = SmallPadding)
                        .width(300.dp)
                        .aspectRatio(list[it].aspect_ratio.toFloat())
                        .clip(RoundedCornerShape(SmallPadding)),
                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.Center
                )
            }
        }
    }
}

@Composable
fun RecommendComp(
    list: List<RecommendModel>,
    modifier: Modifier = Modifier,
    onclick: (String, Int) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = SmallPadding),
            text = "You might also like",
            style = MaterialTheme.typography.h3,
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(SmallPadding)
        ) {
            items(list.size) {
                val rec = list[it]
                if (rec.logoPath != "") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onclick(rec.mediaType, rec.id)
                            },
                        verticalArrangement = Arrangement.Top
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = "${Constants.POSTER_Logo}${rec.logoPath}"
                            ),
                            contentDescription = rec.name,
                            modifier = Modifier
                                .padding(end = SmallPadding)
                                .width(300.dp)
                                .aspectRatio(1.5f)
                                .clip(RoundedCornerShape(SmallPadding)),
                            contentScale = ContentScale.FillBounds,
                            alignment = Alignment.Center
                        )
                        Spacer(modifier = Modifier.size(ExtraSmallPadding))
                        Text(
                            text = rec.name,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(start = SmallPadding)
                        )
                    }

                }
            }
        }
    }
}