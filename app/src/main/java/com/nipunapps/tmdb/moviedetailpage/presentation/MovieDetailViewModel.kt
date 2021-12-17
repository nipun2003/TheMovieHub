package com.nipunapps.tmdb.moviedetailpage.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.homepage.presentation.state.TrendingState
import com.nipunapps.tmdb.moviedetailpage.domain.use_cases.GetMovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetail,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieDetailState = mutableStateOf(MovieDetailState())
    val movieDetailState: State<MovieDetailState> = _movieDetailState

    init {
        savedStateHandle.get<Int>("movieId")?.let { id ->
            getMovieDetail(id = id)
        }
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
}