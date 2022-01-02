package com.nipunapps.tmdb.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.nipunapps.tmdb.R
import com.nipunapps.tmdb.core.Constants.MOVIE
import com.nipunapps.tmdb.core.Constants.TV
import com.nipunapps.tmdb.feature_search.presentation.SearchScreen
import com.nipunapps.tmdb.homepage.presentation.Homepage
import com.nipunapps.tmdb.moviedetailpage.presentation.screens.ImageFullScreen
import com.nipunapps.tmdb.moviedetailpage.presentation.screens.MovieDetailScreen
import com.nipunapps.tmdb.moviedetailpage.presentation.screens.TvDetailScreen
import com.nipunapps.tmdb.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalPermissionsApi
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
                var toolbarBackground by remember {
                    mutableStateOf(false)
                }

                val backgroundColor by animateColorAsState(if (toolbarBackground) ToolbarColor else Color.Transparent)
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        val navController = rememberAnimatedNavController()
                        if (toolbarVisibility) {
                            Toolbar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(backgroundColor)
                                    .padding(
                                        start = BigPadding,
                                        top = PaddingStatusBar,
                                        bottom = SmallPadding
                                    )
                                    .align(Alignment.TopStart)
                            ) {
                                navController.navigate(Screen.SearchResult.route)
                            }
                        }

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
                                toolbarBackground = false
                                Homepage(navController = navController){
                                    toolbarBackground = it
                                }
                            }
                            composable(
                                route = Screen.SearchResult.route,
                                enterTransition = { enterAnimation(300) },
                                popExitTransition = { exitAnimation(300) },
                                exitTransition = { exitAnimation(-300) },
                                popEnterTransition = { enterAnimation(-300) }
                            ) {
                                toolbarVisibility = false
                                SearchScreen(navController = navController)
                            }
                            composable(
                                route = Screen.MovieDetailScreen.route + "/{media_type}/{movieId}",
                                arguments = listOf(navArgument("movieId") {
                                    type = NavType.IntType
                                }),
                                exitTransition = { exitAnimation(-300) },
                                popEnterTransition = { enterAnimation(-300) },
                                enterTransition = { enterAnimation(300) },
                                popExitTransition = { exitAnimation(300) }
                            ) { stack ->
                                stack.arguments?.getString("media_type")?.let { type ->
                                    stack.arguments?.getInt("movieId")?.let { id ->
                                        toolbarVisibility = true
                                        when (type) {
                                            MOVIE -> {
                                                MovieDetailScreen(
                                                    navController = navController,
                                                    movieId = id
                                                ) {
                                                    toolbarBackground = it
                                                }
                                            }
                                            TV -> {
                                                TvDetailScreen(
                                                    navController = navController,
                                                    tvId = id,
                                                ) {
                                                    toolbarBackground = it
                                                }
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
                            composable(
                                route = Screen.ImageFullScreen.route +"/{image}"
                            ){
                                toolbarVisibility = false
                                ImageFullScreen()
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
    modifier: Modifier = Modifier,
    onSearchClick: (Unit) -> Unit
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
        Box(
            modifier =
            Modifier
                .width(100.dp)
                .height(60.dp)
                .padding(SmallPadding)
                .clickable {
                    onSearchClick(Unit)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                modifier = Modifier
                    .size(40.dp)
                    .padding(
                        top = ExtraSmallPadding,
                        bottom = ExtraSmallPadding,
                        end = SmallPadding
                    )
                    .align(Alignment.CenterEnd)
            )
        }
    }
}