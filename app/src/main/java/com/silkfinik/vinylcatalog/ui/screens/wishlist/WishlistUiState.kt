package com.silkfinik.vinylcatalog.ui.screens.wishlist

import com.silkfinik.vinylcatalog.domain.model.VinylRecord

data class WishlistUiState(
    val records: List<VinylRecord> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
