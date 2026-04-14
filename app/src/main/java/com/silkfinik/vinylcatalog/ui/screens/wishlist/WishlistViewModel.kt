package com.silkfinik.vinylcatalog.ui.screens.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.usecase.DeleteRecordUseCase
import com.silkfinik.vinylcatalog.domain.usecase.GetWishlistUseCase
import com.silkfinik.vinylcatalog.domain.usecase.SaveRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    getWishlistUseCase: GetWishlistUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val deleteRecordUseCase: DeleteRecordUseCase
) : ViewModel() {

    val uiState: StateFlow<WishlistUiState> = getWishlistUseCase()
        .map { records -> WishlistUiState(records = records, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WishlistUiState()
        )

    fun moveToCollection(record: VinylRecord) {
        viewModelScope.launch {
            // Update the record's flag and save it
            saveRecordUseCase(record.copy(isInWishlist = false))
        }
    }

    fun removeFromWishlist(record: VinylRecord) {
        viewModelScope.launch {
            deleteRecordUseCase(record)
        }
    }
}
