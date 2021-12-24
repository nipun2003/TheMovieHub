package com.nipunapps.tmdb.feature_search.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipunapps.tmdb.core.Resource
import com.nipunapps.tmdb.feature_search.data.local.QueryInfoDao
import com.nipunapps.tmdb.feature_search.domain.use_cases.SearchAllUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchQueryUseCase: SearchAllUseCase,
    private val dao: QueryInfoDao
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _searchResult = mutableStateOf(SearchQueryState())
    val searchResult: State<SearchQueryState> = _searchResult

    private var searchJob: Job? = null

    private val _prevQuery = mutableStateOf(emptyList<String>())
    val prevQuery: State<List<String>> = _prevQuery

    private val _relatedQuery = mutableStateOf(emptyList<String>())
    val relatedQuery: State<List<String>> = _relatedQuery

    private val _relatedQueryVisibility = mutableStateOf(relatedQuery.value.isNotEmpty())
    val relatedQueryVisibility: State<Boolean> = _relatedQueryVisibility

    private val _chipsVisibility = mutableStateOf(false)
    val chipsVisibility: State<Boolean> = _chipsVisibility

    private val _closeIconState = mutableStateOf(false)
    val closeIconState: State<Boolean> = _closeIconState


    init {
        viewModelScope.launch {
            _prevQuery.value = dao.getPreviousSearch().map {
                it.query
            }.reversed()
            if (prevQuery.value.isNotEmpty()) {
                Log.e("prev", prevQuery.value[0])
                onSearch(prevQuery.value[0], isRecent = true)
            }
        }
    }

    fun onFocus(focus: Boolean) {
        _chipsVisibility.value = focus
        _relatedQueryVisibility.value = relatedQuery.value.isNotEmpty()
    }

    fun updateQuery(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _relatedQuery.value = emptyList()
        } else {
            getRelatedQuery(query)
        }
        onFocus(query.isNullOrBlank())
        _closeIconState.value = query.isNotEmpty()
    }

    private fun getRelatedQuery(query: String) {
        viewModelScope.launch {
            _relatedQuery.value = dao.getRelatedQuery(query).map { it.query }
        }
    }

    fun onSearch(query: String, isRecent: Boolean = false) {
        if (query.isEmpty()) {
            return
        }
        if (!isRecent) {
            _searchQuery.value = query
            _closeIconState.value = query.isNotEmpty()
            _relatedQuery.value = emptyList()
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            searchQueryUseCase(query).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _searchResult.value = SearchQueryState(
                            isLoading = false,
                            data = result.data?.results ?: emptyList(),
                            isFromSuccess = true,
                            isRecent = isRecent
                        )
                        _prevQuery.value = dao.getPreviousSearch().map { it.query }
                    }
                    is Resource.Error -> {
                        _searchResult.value = SearchQueryState(
                            isLoading = false,
                            message = result.message ?: "Something unexpected happened"
                        )
                    }
                    is Resource.Loading -> {
                        _searchResult.value = SearchQueryState(isLoading = true, data = emptyList())
                    }
                }
            }.launchIn(this)
        }
    }

    fun cancelSearch() {
        _searchQuery.value = ""
        _closeIconState.value = false
        searchJob?.cancel()
        searchJob = null
        _searchResult.value = SearchQueryState(
            isLoading = false,
            isFromSuccess = false,
            data = searchResult.value.data
        )
    }
}