package com.nipunapps.tmdb.moviedetailpage.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.moviedetailpage.domain.model.RecommendModel
import com.nipunapps.tmdb.moviedetailpage.domain.use_cases.GetMovieDetail
import com.nipunapps.tmdb.moviedetailpage.domain.use_cases.GetRecommendUseCase
import com.nipunapps.tmdb.moviedetailpage.presentation.states.MovieDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetail,
    private val recommendUseCase: GetRecommendUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieDetailState = mutableStateOf(MovieDetailState())
    val movieDetailState: State<MovieDetailState> = _movieDetailState

    private val _recommendation = mutableStateOf(emptyList<RecommendModel>())
    val recommendation : State<List<RecommendModel>> = _recommendation

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
                    movieDetailState.value.data?.id?.let { id->
                        getRecommendation(id = id)
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

    private fun getRecommendation(type : String = Constants.MOVIE, id: Int){
        recommendUseCase(type,id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _recommendation.value = result.data?: emptyList<RecommendModel>()
                }
                else -> {Unit}
            }
        }.launchIn(viewModelScope)
    }
}