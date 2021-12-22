package com.nipunapps.tmdb.moviedetailpage.presentation.components

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.nipunapps.tmdb.R
import com.nipunapps.tmdb.moviedetailpage.data.dto.movie_detail.Result
import com.nipunapps.tmdb.ui.theme.BigPadding
import com.nipunapps.tmdb.ui.theme.SmallPadding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

const val YT_START = "https://img.youtube.com/vi/"
const val YT_END = "/hqdefault.jpg"

@Composable
fun VideoComp(
    videos: List<Result>,
    modifier: Modifier = Modifier,
//    onclick: (String) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
    val key = remember {
        mutableStateOf("")
    }
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(horizontal = SmallPadding),
                text = "Videos",
                style = MaterialTheme.typography.h3,
            )
            Spacer(modifier = Modifier.size(SmallPadding))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = SmallPadding),
                horizontalArrangement = Arrangement.Start
            ) {
                items(videos.size) {
                    SingleVideo(
                        video = videos[it],
                        modifier = Modifier
                            .clip(RoundedCornerShape(SmallPadding))
                            .width(250.dp)
                            .aspectRatio(1.4f)
                            .padding(end = BigPadding)
                    ) {
                        openDialog.value = true
                        key.value = it
//                        onclick(it)
                    }
                }
            }
        }
        AlertDialogComponent(openDialog = openDialog, key.value)
    }
}

@Composable
fun SingleVideo(
    video: Result,
    modifier: Modifier = Modifier,
    onclick: (String) -> Unit
) {
    val url = "$YT_START${video.key}$YT_END"
    Box(
        modifier = modifier
    ) {
        Image(
            painter = rememberImagePainter(
                data = url
            ),
            modifier = Modifier
                .width(250.dp)
                .aspectRatio(1.4f)
                .clip(RoundedCornerShape(SmallPadding)),
            contentDescription = video.name,
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.Center
        )

        Image(
            painter = painterResource(
                id = R.drawable.ic_play
            ),
            contentDescription = "Play",
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center)
                .clickable {
                    onclick(video.key)
                }
        )
    }
}

@Composable
fun AlertDialogComponent(
    openDialog: MutableState<Boolean>,
    key: String
) {
    if (openDialog.value) {
        Dialog(
            onDismissRequest = {
                openDialog.value = false
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .padding(SmallPadding)
            ) {
                AndroidView(factory = { context ->
                    YouTubePlayerView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(key, 0F)
                            }
                        })
                    }
                })
            }
        }
    }
}