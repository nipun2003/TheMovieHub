package com.nipunapps.tmdb.core

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nipunapps.tmdb.ui.theme.ExtraSmallPadding
import com.nipunapps.tmdb.ui.theme.SmallPadding


@Preview
@Composable
fun Plus() {
    Canvas(
        modifier = Modifier.size(24.dp).padding(ExtraSmallPadding)
    ) {
        drawLine(
            color = Color.White,
            start = Offset(this.size.width * 0.5f, 0f),
            end = Offset(this.size.width * 0.5f, this.size.height * 1f),
            strokeWidth = 8f,
            cap = StrokeCap.Square
        )
        drawLine(
            color = Color.White,
            start = Offset(y = this.size.height * 0.5f, x = 0f),
            end = Offset(y = this.size.height * 0.5f, x = this.size.height * 1f),
            strokeWidth = 8f,
            cap = StrokeCap.Square
        )
    }
}

@Preview
@Composable
fun InfoIcon() {
    Canvas(
        modifier = Modifier.size(24.dp).padding(SmallPadding)
    ) {
        val paint = Paint().apply {
            textAlign = Paint.Align.CENTER
            textSize = 36f
            color = Color.White.toArgb()

        }
        drawCircle(
            color = Color.White,
            radius = 24f,
            style = Stroke(width = 5f, cap = StrokeCap.Round)
        )
        drawContext.canvas.nativeCanvas.drawText("i", center.x, center.y + 12f, paint)
    }
}

@Preview
@Composable
fun instagramIcon() {
    val instaColors = listOf(Color.Yellow, Color.Red, Color.Magenta)
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp)
    ) {
        drawRoundRect(
            brush = Brush.linearGradient(colors = instaColors),
            cornerRadius = CornerRadius(60f, 60f),
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )
        drawCircle(
            brush = Brush.linearGradient(colors = instaColors),
            radius = 45f,
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )
        drawCircle(
            brush = Brush.linearGradient(colors = instaColors),
            radius = 13f,
            center = Offset(this.size.width * .80f, this.size.height * 0.20f),
        )
    }
}
