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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.moviedetailpage.data.dto.movie_detail.ProductionCompany
import com.nipunapps.tmdb.ui.theme.ExtraSmallPadding
import com.nipunapps.tmdb.ui.theme.SmallPadding
import com.nipunapps.tmdb.ui.theme.ToolbarDarkVariant

@Composable
fun ProductionCompany(
    modifier: Modifier = Modifier,
    productionCompany: List<ProductionCompany>
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(horizontal = SmallPadding),
            text = "Production Company",
            style = MaterialTheme.typography.h3,
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = SmallPadding),
            horizontalArrangement = Arrangement.Start
        ) {
            items(productionCompany.size) {
                SingleCompany(
                    productionCompany = productionCompany[it],
                    modifier = Modifier
                        .padding(end = SmallPadding)
                )
            }
        }
    }
}

@Composable
fun SingleCompany(
    modifier: Modifier = Modifier,
    productionCompany: ProductionCompany
) {
    Box(modifier = modifier) {
        if (productionCompany.logo_path != null && productionCompany.logo_path != "") {
            Image(
                painter = rememberImagePainter(
                    data = "${Constants.IMAGE_PATH}${productionCompany.logo_path}"
                ),
                contentDescription = productionCompany.name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(140.dp)
                    .aspectRatio(1.5f)
                    .align(Alignment.TopStart)
            )
        }else {
            Card(
                modifier = Modifier
                    .padding(end = SmallPadding)
                    .clip(RoundedCornerShape(SmallPadding))
                    .padding(ExtraSmallPadding),
                elevation = ExtraSmallPadding,
                backgroundColor = ToolbarDarkVariant
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Green,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Normal
                            )
                        ) {
                            append(productionCompany.name[0])
                        }

                        append(productionCompany.name.substring(1))
                    },
                    modifier = Modifier.padding(horizontal = ExtraSmallPadding),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}