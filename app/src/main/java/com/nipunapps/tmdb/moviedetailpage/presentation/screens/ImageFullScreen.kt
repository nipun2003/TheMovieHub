package com.nipunapps.tmdb.moviedetailpage.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.nipunapps.tmdb.R
import com.nipunapps.tmdb.core.Constants.ROOT_PATH
import com.nipunapps.tmdb.core.noRippleClickable
import com.nipunapps.tmdb.core.showToast
import com.nipunapps.tmdb.moviedetailpage.presentation.viewmodels.DownloadStatus.*
import com.nipunapps.tmdb.moviedetailpage.presentation.viewmodels.ImageFullViewModel
import com.nipunapps.tmdb.ui.PermissionDialog
import com.nipunapps.tmdb.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@ExperimentalPermissionsApi
@Composable
fun ImageFullScreen(
    viewModel: ImageFullViewModel = hiltViewModel()
) {
    val image = viewModel.image.value
    val context = LocalContext.current
    Column(
        Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(ExtraBigPadding))
        val scale = remember { mutableStateOf(1f) }
        val rotationState = remember { mutableStateOf(0f) }
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        if (viewModel.startDownload.value) {
            PermissionDialog {
                if (it) viewModel.downloadImage()
            }
        }
        AnimatedVisibility(visible = viewModel.lockVisibility.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ActionBarSize)
                    .padding(SmallPadding)
                    .zIndex(10f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(
                        id = if (!viewModel.lock.value) R.drawable.ic_unlock else R.drawable.ic_lock
                    ),
                    contentDescription = "Lock",
                    modifier = Modifier
                        .size(ExtraBigPadding)
                        .noRippleClickable {
                            viewModel.toggleLock()
                        }
                )
                Spacer(modifier = Modifier.size(SmallPadding))
                Image(
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = "Download",
                    modifier = Modifier
                        .size(ExtraBigPadding)
                        .noRippleClickable {
                            viewModel.startDownload()
                        }
                )
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SmallPadding)
                .align(Alignment.CenterHorizontally),
            visible = viewModel.downloadStatus.value != NOTHING
        ) {
            if (viewModel.downloadProgress.value > 0f) {
                DownloadProgress(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp),
                    progress = viewModel.downloadProgress.value
                )
                Spacer(modifier = Modifier.size(ExtraSmallPadding))
            }
            Text(
                text =
                when (viewModel.downloadStatus.value) {
                    STARTED -> {
                        "Download Started"
                    }
                    FINISHED -> {
                        "File saved at $ROOT_PATH"
                    }
                    EXISTS -> {
                        "File already exists"
                    }
                    ERROR -> {
                        "Error download image"
                    }
                    else -> ""
                },
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(ExtraSmallPadding)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, rotation ->
                        if (!viewModel.lock.value) {
                            scale.value *= zoom
                            rotationState.value += rotation
                            val x = pan.x * zoom
                            val y = pan.y * zoom
                            val angleRad = rotationState.value * PI / 180.0
                            offsetX += (x * cos(angleRad) - y * sin(angleRad)).toFloat()
                            offsetY += (x * sin(angleRad) + y * cos(angleRad)).toFloat()
                        }
                    }
                }
                .noRippleClickable {
                    viewModel.showLock()
                }
        ) {
            Image(
                painter = rememberImagePainter(
                    data = image
                ),
                contentDescription = image,
                modifier = Modifier
                    .align(Center)
                    .zIndex(-3f)
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .graphicsLayer(
                        // adding some zoom limits (min 50%, max 200%)
                        scaleX = maxOf(.5f, minOf(3f, scale.value)),
                        scaleY = maxOf(.5f, minOf(3f, scale.value)),
                        rotationZ = rotationState.value
                    )
            )
        }
    }
    LaunchedEffect(Unit) {

    }
}

@Composable
fun DownloadProgress(
    modifier: Modifier = Modifier,
    progress: Float
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val drawProgress = (progress * constraints.maxWidth).toFloat()
        Canvas(modifier = modifier) {
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = DividerColor
                ),
                size = Size(
                    drawProgress,
                    constraints.maxHeight.toFloat()
                ),
                cornerRadius = CornerRadius(1f, 1f)
            )
        }
    }
}