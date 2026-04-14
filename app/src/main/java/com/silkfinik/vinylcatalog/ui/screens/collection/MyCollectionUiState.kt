package com.silkfinik.vinylcatalog.ui.screens.collection

import com.silkfinik.vinylcatalog.domain.model.VinylRecord

data class MyCollectionUiState(
    val records: List<VinylRecord> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
