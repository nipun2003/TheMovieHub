package com.nipunapps.tmdb.homepage.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.domain.use_cases.GetUpcoming
import com.nipunapps.tmdb.homepage.presentation.state.TrendingState
import com.nipunapps.tmdb.homepage.presentation.state.UpcomingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.nipunapps.tmdb.core.Constants.DAY
import com.nipunapps.tmdb.core.Constants.ALL
import com.nipunapps.tmdb.homepage.domain.use_cases.GetTrending
import com.nipunapps.tmdb.moviedetailpage.domain.use_cases.GetMovieDetail
import com.nipunapps.tmdb.moviedetailpage.presentation.states.MovieDetailState

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val upcomingUseCase : GetUpcoming,
    private val trendingUseCase : GetTrending,
    private val getMovieDetailUseCase: GetMovieDetail
) : ViewModel() {

    private val _upcomingMovies = mutableStateOf(UpcomingState())
    val upcomingMovies : State<UpcomingState> = _upcomingMovies

    private val _nowPlaying = mutableStateOf(UpcomingState())
    val nowPlaying : State<UpcomingState> = _nowPlaying

    private val _movieDetailState = mutableStateOf(MovieDetailState())
    val movieDetailState: State<MovieDetailState> = _movieDetailState


    private val _trendingMovies = mutableStateOf(TrendingState())
    val trendingMovies : State<TrendingState> = _trendingMovies

    init {
        getNowPlaying()
        getUpcomingMovies()
        getTrendingMovies(
            type = ALL,
            time = DAY
        )
    }

    private fun getNowPlaying(){
        upcomingUseCase.getNowPlaying().onEach { result->
            when(result){
                is Resource.Success ->{
                    result.data?.let { data ->
                        data.results.forEach { res->
                            getMovieDetail(res.id)
                        }
                    }
                }
                is Resource.Error ->{
                    _movieDetailState.value = MovieDetailState(
                        isLoading = false,
                        message = result.message?:"Something unexpected happened"
                    )
                }
                is Resource.Loading ->{
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
        trendingUseCase(type,time).onEach { result ->
            when(result){
                is Resource.Success ->{
                    _trendingMovies.value = TrendingState(
                        isLoading = false,
                        data = result.data?.results?: emptyList()
                    )
                }
                is Resource.Error ->{
                    _trendingMovies.value = TrendingState(
                        isLoading = false,
                        message = result.message?:"Something unexpected happened"
                    )
                }
                is Resource.Loading ->{
                    _trendingMovies.value = TrendingState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getUpcomingMovies(){
        upcomingUseCase().onEach { result->
            when(result){
                is Resource.Success ->{
                    _upcomingMovies.value = UpcomingState(
                        isLoading = false,
                        results = result.data?.results?: emptyList()
                    )
                }
                is Resource.Error ->{
                    _upcomingMovies.value = UpcomingState(
                        isLoading = false,
                        message = result.message?:"Something unexpected happened"
                    )
                }
                is Resource.Loading ->{
                    _upcomingMovies.value = UpcomingState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}