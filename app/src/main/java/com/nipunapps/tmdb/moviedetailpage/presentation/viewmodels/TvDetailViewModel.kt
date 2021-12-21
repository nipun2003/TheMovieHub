package com.nipunapps.tmdb.moviedetailpage.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.moviedetailpage.domain.model.RecommendModel
import com.nipunapps.tmdb.moviedetailpage.domain.use_cases.GetRecommendUseCase
import com.nipunapps.tmdb.moviedetailpage.domain.use_cases.GetTvDetails
import com.nipunapps.tmdb.moviedetailpage.presentation.states.TvDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TvDetailViewModel @Inject constructor(
    private val tvDetailUseCase : GetTvDetails,
    private val recommendUseCase: GetRecommendUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _tvDetailState = mutableStateOf(TvDetailState())
    val tvDetailState: State<TvDetailState> = _tvDetailState

    private val _recommendation = mutableStateOf(emptyList<RecommendModel>())
    val recommendation : State<List<RecommendModel>> = _recommendation

    init {
        savedStateHandle.get<Int>("movieId")?.let { id ->
            getTvDetails(id = id)
        }
    }

    private fun getTvDetails(id : Int){
        tvDetailUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _tvDetailState.value = TvDetailState(
                        isLoading = false,
                        data = result.data
                    )
                    tvDetailState.value.data?.id?.let { id->
                        getRecommendation(id = id)
                    }
                }
                is Resource.Error -> {
                    _tvDetailState.value = TvDetailState(
                        isLoading = false,
                        message = result.message ?: "Something unexpected happened"
                    )
                }
                is Resource.Loading -> {
                    _tvDetailState.value = TvDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRecommendation(type : String = Constants.TV,id: Int){
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