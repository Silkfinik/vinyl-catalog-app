package com.silkfinik.vinylcatalog.data.mapper

import com.silkfinik.vinylcatalog.data.local.entity.VinylRecordEntity
import com.silkfinik.vinylcatalog.data.remote.dto.DiscogsReleaseDto
import com.silkfinik.vinylcatalog.domain.model.VinylRecord

fun VinylRecordEntity.toDomain(): VinylRecord {
    return VinylRecord(
        id = id,
        title = title,
        artist = artist,
        coverUrl = coverUrl,
        year = year,
        label = label,
        genre = genre,
        format = format,
        rating = rating,
        notes = notes,
        isInWishlist = isInWishlist
    )
}

fun VinylRecord.toEntity(): VinylRecordEntity {
    return VinylRecordEntity(
        id = id,
        title = title,
        artist = artist,
        coverUrl = coverUrl,
        year = year,
        label = label,
        genre = genre,
        format = format,
        rating = rating,
        notes = notes,
        isInWishlist = isInWishlist
    )
}

fun DiscogsReleaseDto.toDomain(): VinylRecord {
    val parts = title.split(" - ", limit = 2)
    val artistName = if (parts.size > 1) parts[0].trim() else "Unknown Artist"
    val albumTitle = if (parts.size > 1) parts[1].trim() else title.trim()

    return VinylRecord(
        id = id.toString(),
        title = albumTitle,
        artist = artistName,
        coverUrl = coverImage,
        year = year,
        label = label?.firstOrNull(),
        genre = null,
        format = format?.firstOrNull(),
        rating = 0,
        notes = null,
        isInWishlist = false
    )
}
