package com.silkfinik.vinylcatalog.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.usecase.SaveRecordUseCase
import com.silkfinik.vinylcatalog.domain.usecase.SearchDiscogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDiscogsUseCase: SearchDiscogsUseCase,
    private val saveRecordUseCase: SaveRecordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }
        searchJob?.cancel()
        if (query.trim().length > 2) {
            searchJob = viewModelScope.launch {
                delay(600) // debounce
                performSearch(query.trim())
            }
        } else {
            _uiState.update { it.copy(results = emptyList(), error = null, isLoading = false) }
        }
    }

    fun setFilter(filter: String) {
        _uiState.update { it.copy(activeFilter = filter) }
        // Local filtering would be applied here in a more advanced scenario.
    }

    private suspend fun performSearch(query: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        val result = searchDiscogsUseCase(query)
        result.fold(
            onSuccess = { records ->
                _uiState.update { it.copy(isLoading = false, results = records) }
            },
            onFailure = { error ->
                _uiState.update { it.copy(isLoading = false, error = error.localizedMessage) }
            }
        )
    }

    fun addToCollection(record: VinylRecord) {
        viewModelScope.launch {
            saveRecordUseCase(record.copy(isInWishlist = false))
        }
    }

    fun addToWishlist(record: VinylRecord) {
        viewModelScope.launch {
            saveRecordUseCase(record.copy(isInWishlist = true))
        }
    }
}
