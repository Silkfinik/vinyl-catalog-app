package com.silkfinik.vinylcatalog.data.mapper

import com.silkfinik.vinylcatalog.data.local.entity.VinylRecordEntity
import com.silkfinik.vinylcatalog.data.remote.dto.DiscogsReleaseDto
import org.junit.Assert.assertEquals
import org.junit.Test

class VinylMapperTest {

    @Test
    fun `map DiscogsReleaseDto to VinylRecord with split artist and title`() {
        val dto = DiscogsReleaseDto(
            id = 100L,
            title = "The Beatles - Abbey Road",
            year = "1969",
            label = listOf("Apple Records"),
            format = listOf("Vinyl"),
            coverImage = "url"
        )
        
        val record = dto.toDomain()
        
        assertEquals("100", record.id)
        assertEquals("The Beatles", record.artist)
        assertEquals("Abbey Road", record.title)
        assertEquals("1969", record.year)
        assertEquals("Apple Records", record.label)
        assertEquals("Vinyl", record.format)
        assertEquals("url", record.coverUrl)
    }

    @Test
    fun `map DiscogsReleaseDto without hyphen returns Unknown Artist`() {
        val dto = DiscogsReleaseDto(
            id = 200L,
            title = "Awesome Soundtrack",
            year = "2000"
        )
        val record = dto.toDomain()
        
        assertEquals("Unknown Artist", record.artist)
        assertEquals("Awesome Soundtrack", record.title)
    }

    @Test
    fun `map Entity to Domain and back`() {
        val entity = VinylRecordEntity(
            id = "1", title = "T", artist = "A", coverUrl = null, year = null,
            label = null, genre = null, format = "LP", rating = 5, notes = "Good", isInWishlist = true
        )
        
        val domain = entity.toDomain()
        val mappedEntity = domain.toEntity()
        
        assertEquals(entity, mappedEntity)
    }
}
