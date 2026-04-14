package com.silkfinik.vinylcatalog.ui.screens.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.usecase.DeleteRecordUseCase
import com.silkfinik.vinylcatalog.domain.usecase.GetCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCollectionViewModel @Inject constructor(
    getCollectionUseCase: GetCollectionUseCase,
    private val deleteRecordUseCase: DeleteRecordUseCase
) : ViewModel() {

    val uiState: StateFlow<MyCollectionUiState> = getCollectionUseCase()
        .map { records -> MyCollectionUiState(records = records, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MyCollectionUiState()
        )

    fun deleteRecord(record: VinylRecord) {
        viewModelScope.launch {
            deleteRecordUseCase(record)
        }
    }
}
