package com.nipunapps.tmdb.moviedetailpage.presentation.components.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.moviedetailpage.data.dto.movie_detail.Cast
import com.nipunapps.tmdb.ui.theme.*

@Composable
fun TopBilledCast(
    modifier: Modifier = Modifier,
    casts: List<Cast>
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(horizontal = SmallPadding),
            text = "Top Billed Cast",
            style = MaterialTheme.typography.h3,
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = SmallPadding),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(casts.size) {
                SingleCard(cast = casts[it])
            }
        }
    }
}

@Composable
fun SingleCard(
    modifier: Modifier = Modifier,
    cast: Cast
) {
    Card(
        modifier = Modifier
            .padding(end = SmallPadding)
            .clip(RoundedCornerShape(SmallPadding))
            .width(130.dp)
            .aspectRatio(0.47f),
        elevation = ExtraSmallPadding,
        backgroundColor = ToolbarDarkVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = rememberImagePainter(
                    data = "${Constants.IMAGE_PATH}${cast.profile_path}"
                ),
                contentDescription = cast.original_name,
                modifier = Modifier
                    .width(130.dp)
                    .aspectRatio(0.82f),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(ExtraSmallPadding)
            ) {
                Text(
                    text = cast.name,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.size(ExtraSmallPadding))
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.W200,
                                fontSize = 16.sp,
                                color = GenreColor
                            )
                        ) {
                            append("Character ")
                        }
                        append("- ${cast.character}")
                    },
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}