package com.silkfinik.vinylcatalog.domain.usecase

import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.repository.VinylRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchDiscogsUseCaseTest {

    private val repository = mockk<VinylRepository>()
    private val useCase = SearchDiscogsUseCase(repository)

    @Test
    fun `when searching discogs, triggers repository and returns success`() = runTest {
        // Arrange
        val query = "Daft Punk"
        val genre = "Electronic"
        val mockRecords = listOf(
            VinylRecord(
                id = "1",
                title = "Discovery",
                artist = "Daft Punk",
                coverUrl = null,
                year = "2001",
                label = "Virgin",
                genre = "Electronic",
                format = "LP",
                rating = 0,
                notes = null,
                isInWishlist = false
            )
        )
        coEvery { repository.searchDiscogs(query, genre) } returns Result.success(mockRecords)

        // Act
        val result = useCase(query, genre)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals("Discovery", result.getOrNull()?.first()?.title)
        
        // Verify interaction
        coVerify(exactly = 1) { repository.searchDiscogs(query, genre) }
    }

    @Test
    fun `when searching discogs fails, returns failure`() = runTest {
        // Arrange
        val query = "Unknown"
        val exception = RuntimeException("Network Error")
        coEvery { repository.searchDiscogs(query, null) } returns Result.failure(exception)

        // Act
        val result = useCase(query, null)

        // Assert
        assertTrue(result.isFailure)
        assertEquals("Network Error", result.exceptionOrNull()?.message)
    }
}
