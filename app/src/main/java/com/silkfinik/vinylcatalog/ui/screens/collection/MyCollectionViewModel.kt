package com.silkfinik.vinylcatalog.ui.screens.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.usecase.DeleteRecordUseCase
import com.silkfinik.vinylcatalog.domain.usecase.GetCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCollectionViewModel @Inject constructor(
    getCollectionUseCase: GetCollectionUseCase,
    private val deleteRecordUseCase: DeleteRecordUseCase
) : ViewModel() {

    enum class SortOrder { DateAdded, Title, Year }

    private val _sortOrder = MutableStateFlow(SortOrder.DateAdded)

    val uiState: StateFlow<MyCollectionUiState> = kotlinx.coroutines.flow.combine(
        getCollectionUseCase(),
        _sortOrder
    ) { records, order ->
        val sortedRecords = when (order) {
            SortOrder.Title -> records.sortedBy { it.title }
            SortOrder.Year -> records.sortedBy { it.year ?: "9999" }
            SortOrder.DateAdded -> records
        }
        MyCollectionUiState(records = sortedRecords, isLoading = false)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MyCollectionUiState()
    )

    fun setSortOrder(order: SortOrder) {
        _sortOrder.update { order }
    }

    fun deleteRecord(record: VinylRecord) {
        viewModelScope.launch {
            deleteRecordUseCase(record)
        }
    }
}
