package com.nipunapps.tmdb.homepage.presentation

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipunapps.tmdb.core.Constants.ALL
import com.nipunapps.tmdb.core.Constants.DAY
import com.nipunapps.tmdb.core.Constants.MOVIE
import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.domain.use_cases.GetTrending
import com.nipunapps.tmdb.homepage.domain.use_cases.GetUpcoming
import com.nipunapps.tmdb.homepage.presentation.state.PresentationState
import com.nipunapps.tmdb.moviedetailpage.domain.use_cases.GetMovieDetail
import com.nipunapps.tmdb.moviedetailpage.presentation.states.MovieDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val upcomingUseCase: GetUpcoming,
    private val trendingUseCase: GetTrending,
    private val getMovieDetailUseCase: GetMovieDetail
) : ViewModel() {

    private val _upcomingMovies = mutableStateOf(PresentationState())
    val upcomingMovies: State<PresentationState> = _upcomingMovies

    private val _nowPlaying = mutableStateOf(PresentationState())
    val nowPlaying: State<PresentationState> = _nowPlaying

    private val _popular = mutableStateOf(PresentationState())
    val popular: State<PresentationState> = _popular

    private val _movieDetailState = mutableStateOf(MovieDetailState())
    val movieDetailState: State<MovieDetailState> = _movieDetailState


    private val _trendingMovies = mutableStateOf(PresentationState())
    val trendingMovies: State<PresentationState> = _trendingMovies

    private val _popularitySelected = mutableStateOf(0)
    val popularitySelected :State<Int> = _popularitySelected

    init {
        getPosterMovie()
        getNowPlaying()
        getPopularity(MOVIE,popularitySelected.value)
        getUpcomingMovies()
        getTrendingMovies(
            type = ALL,
            time = DAY
        )
    }

    private fun getNowPlaying() {
        upcomingUseCase.getNowPlaying().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _nowPlaying.value = PresentationState(
                        isLoading = false,
                        data = result.data?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _nowPlaying.value = PresentationState(
                        isLoading = false,
                        message = result.message ?: "Something unexpected happened"
                    )
                }
                is Resource.Loading -> {
                    _nowPlaying.value = PresentationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getPopularity(type: String,selected : Int){
        _popularitySelected.value = selected
        upcomingUseCase.getPopularity(type).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _popular.value = PresentationState(
                        isLoading = false,
                        data = result.data?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _popular.value = PresentationState(
                        isLoading = false,
                        message = result.message ?: "Something unexpected happened"
                    )
                }
                is Resource.Loading -> {
                    _popular.value = PresentationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPosterMovie() {
        upcomingUseCase.getPosterMovie().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { data ->
                        getMovieDetail(data.id)
                    }
                }
                is Resource.Error -> {
                    _movieDetailState.value = MovieDetailState(
                        isLoading = false,
                        message = result.message ?: "Something unexpected happened"
                    )
                }
                is Resource.Loading -> {
                    _movieDetailState.value = MovieDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMovieDetail(id: Int) {
        getMovieDetailUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _movieDetailState.value = MovieDetailState(
                        isLoading = false,
                        data = result.data
                    )
                }
                is Resource.Error -> {
                    _movieDetailState.value = MovieDetailState(
                        isLoading = false,
                        message = result.message ?: "Something unexpected happened"
                    )
                }
                is Resource.Loading -> {
                    _movieDetailState.value = MovieDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTrendingMovies(type: String, time: String) {
        trendingUseCase(type, time).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _trendingMovies.value = PresentationState(
                        isLoading = false,
                        data = result.data?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _trendingMovies.value = PresentationState(
                        isLoading = false,
                        message = result.message ?: "Something unexpected happened"
                    )
                }
                is Resource.Loading -> {
                    _trendingMovies.value = PresentationState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getUpcomingMovies() {
        upcomingUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _upcomingMovies.value = PresentationState(
                        isLoading = false,
                        data = result.data?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _upcomingMovies.value = PresentationState(
                        isLoading = false,
                        message = result.message ?: "Something unexpected happened"
                    )
                }
                is Resource.Loading -> {
                    _upcomingMovies.value = PresentationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}