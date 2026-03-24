package com.silkfinik.vinylcatalog.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscogsSearchResponse(
    val results: List<DiscogsReleaseDto>
)

@Serializable
data class DiscogsReleaseDto(
    val id: Long,
    val title: String,
    val year: String? = null,
    val label: List<String>? = null,
    val format: List<String>? = null,
    @SerialName("cover_image") val coverImage: String? = null
)
