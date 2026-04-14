package com.silkfinik.vinylcatalog.ui.screens.details

import com.silkfinik.vinylcatalog.domain.model.VinylRecord

data class RecordDetailsUiState(
    val record: VinylRecord? = null,
    val selectedFormat: String = "LP",
    val rating: Int = 0,
    val notes: String = "",
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
    val error: String? = null
)
