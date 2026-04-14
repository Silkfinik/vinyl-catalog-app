package com.silkfinik.vinylcatalog.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.usecase.GetRecordDetailsUseCase
import com.silkfinik.vinylcatalog.domain.usecase.SaveRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRecordDetailsUseCase: GetRecordDetailsUseCase,
    private val saveRecordUseCase: SaveRecordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecordDetailsUiState())
    val uiState: StateFlow<RecordDetailsUiState> = _uiState.asStateFlow()

    fun loadRecord(recordId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val record = getRecordDetailsUseCase(recordId)
            if (record != null) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        record = record,
                        selectedFormat = record.format ?: "LP",
                        rating = record.rating,
                        notes = record.notes ?: ""
                    ) 
                }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Record not found.") }
            }
        }
    }

    fun updateFormat(format: String) {
        _uiState.update { it.copy(selectedFormat = format) }
    }

    fun updateRating(rating: Int) {
        _uiState.update { it.copy(rating = rating) }
    }

    fun updateNotes(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    fun saveChanges() {
        val currentState = _uiState.value
        val record = currentState.record ?: return
        viewModelScope.launch {
            val updatedRecord = record.copy(
                format = currentState.selectedFormat,
                rating = currentState.rating,
                notes = currentState.notes
            )
            saveRecordUseCase(updatedRecord)
            _uiState.update { it.copy(isSaved = true) }
        }
    }
}
