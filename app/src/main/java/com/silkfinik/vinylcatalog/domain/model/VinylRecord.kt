package com.silkfinik.vinylcatalog.domain.model

data class VinylRecord(
    val id: String,
    val title: String,
    val artist: String,
    val coverUrl: String?,
    val year: String?,
    val label: String?,
    val genre: String?,
    val format: String? = null,
    val rating: Int = 0,
    val notes: String? = null,
    val isInWishlist: Boolean = false
)
