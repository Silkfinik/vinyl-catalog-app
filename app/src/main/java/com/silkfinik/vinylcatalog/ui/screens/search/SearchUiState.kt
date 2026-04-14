package com.silkfinik.vinylcatalog.ui.screens.search

import com.silkfinik.vinylcatalog.domain.model.VinylRecord

data class SearchUiState(
    val query: String = "",
    val activeFilter: String = "All Results",
    val results: List<VinylRecord> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
