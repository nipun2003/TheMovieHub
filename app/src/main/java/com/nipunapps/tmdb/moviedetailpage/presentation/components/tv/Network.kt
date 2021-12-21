package com.nipunapps.tmdb.moviedetailpage.presentation.components.tv

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.moviedetailpage.data.dto.tv_detail.Network
import com.nipunapps.tmdb.ui.theme.ExtraSmallPadding
import com.nipunapps.tmdb.ui.theme.SmallPadding
import com.nipunapps.tmdb.ui.theme.ToolbarColor
import com.nipunapps.tmdb.ui.theme.ToolbarDarkVariant

@Composable
fun AvailableOn(
    modifier: Modifier = Modifier,
    networks: List<Network>,
    homepage: String
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = SmallPadding),
            text = "Available On",
            style = MaterialTheme.typography.h3,
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = SmallPadding),
            horizontalArrangement = Arrangement.Start
        ) {
            items(networks.size) {
                SingleNetwork(network = networks[it],homepage)
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun SingleNetwork(
    network: Network,
    homepage: String
) {
    val uriHandler = LocalUriHandler.current
    var elevation by remember {
        mutableStateOf(ExtraSmallPadding)
    }
    Column(
        modifier = Modifier
            .padding(SmallPadding),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .width(150.dp)
                .aspectRatio(1.5f),
            painter = rememberImagePainter(
                data = "${Constants.LOGO_PATH}${network.logo_path}"
            ),
            contentDescription = network.name,
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        Surface(
            modifier = Modifier
                .clickable {
                    uriHandler.openUri(Uri.parse(homepage).toString())
                },
            shape = RoundedCornerShape(SmallPadding),
            elevation = elevation,
            color = ToolbarColor
        ) {
            Text(
                text = "Watch Now",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(horizontal = SmallPadding, vertical = ExtraSmallPadding)
            )
        }
    }
}