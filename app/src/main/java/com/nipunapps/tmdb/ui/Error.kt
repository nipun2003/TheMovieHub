package com.nipunapps.tmdb.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.nipunapps.tmdb.R
import com.nipunapps.tmdb.ui.theme.ExtraSmallPadding
import com.nipunapps.tmdb.ui.theme.SmallPadding

@Composable
fun Error(
    modifier: Modifier,
    message: String,
    reset: (Unit) -> Unit
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(SmallPadding))
            val mainButtonColor = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = MaterialTheme.colors.surface
            )
            Button(
                colors = mainButtonColor,
                onClick = {
                    reset(Unit)
                },
                modifier = Modifier
                    .padding(ExtraSmallPadding)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_refresh),
                    contentDescription = "Refresh",
                    tint = Color.White
                )
                Text(
                    text = "Retry",
                    modifier = Modifier.padding(start = ExtraSmallPadding)
                )
            }
        }
    }
}