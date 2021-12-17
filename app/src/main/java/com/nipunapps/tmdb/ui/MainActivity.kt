package com.nipunapps.tmdb.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nipunapps.tmdb.R
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.homepage.presentation.Homepage
import com.nipunapps.tmdb.moviedetailpage.presentation.MovieDetailScreen
import com.nipunapps.tmdb.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = resources.getColor(R.color.transparent)
        }
        setContent {
            TMDBTheme {
                var toolbarVisibility by remember {
                    mutableStateOf(true)
                }
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (toolbarVisibility) {
                            Toolbar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = BigPadding,
                                        top = PaddingStatusBar,
                                        bottom = SmallPadding
                                    )
                                    .align(Alignment.TopStart)
                            )
                        }

                        val navController = rememberAnimatedNavController()
                        AnimatedNavHost(
                            navController = navController,
                            startDestination = Screen.HomeScreen.route,
                            modifier = Modifier
                                .fillMaxSize()
                                .zIndex(-1f)
                        ) {
                            composable(
                                route = Screen.HomeScreen.route,
                                exitTransition = { exitAnimation(-300) },
                                popEnterTransition = { enterAnimation(-300) }
                            ) {
                                toolbarVisibility = true
                                Homepage(navController = navController)
                            }
                            composable(
                                route = Screen.MovieDetailScreen.route + "/{media_type}/{movieId}",
                                arguments = listOf(navArgument("movieId") {
                                    type = NavType.IntType
                                }),
                                enterTransition = { enterAnimation(300)},
                                popExitTransition = { exitAnimation(300)}
                            ) { stack ->
                                stack.arguments?.getString("media_type")?.let { type ->
                                    stack.arguments?.getInt("movieId")?.let { id ->
                                        toolbarVisibility = true
                                        when (type) {
                                            Constants.MOVIE -> {
                                                MovieDetailScreen(
                                                    navController = navController,
                                                    movieId = id
                                                )
                                            }
                                            else -> {
                                                Box(modifier = Modifier.fillMaxSize()) {
                                                    Text(
                                                        text = "Soon....",
                                                        style = MaterialTheme.typography.h2,
                                                        modifier = Modifier.align(
                                                            Alignment.Center
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Toolbar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.tmdblogo),
            contentDescription = "App logo",
        )
    }
}