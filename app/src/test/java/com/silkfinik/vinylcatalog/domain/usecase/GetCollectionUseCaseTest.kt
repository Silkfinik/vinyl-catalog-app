package com.silkfinik.vinylcatalog.domain.usecase

import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.repository.VinylRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCollectionUseCaseTest {

    private val repository = mockk<VinylRepository>()
    private val useCase = GetCollectionUseCase(repository)

    @Test
    fun `invoke returns flow of records from repository`() = runTest {
        // Arrange
        val mockRecords = listOf(
            VinylRecord(
                id = "1", title = "1989", artist = "Taylor Swift", coverUrl = null,
                year = "2014", label = null, genre = "Pop", format = "LP", rating = 5, notes = null, isInWishlist = false
            )
        )
        every { repository.getCollection() } returns flowOf(mockRecords)

        // Act
        val resultFlow = useCase()
        val result = resultFlow.first()

        // Assert
        assertEquals(1, result.size)
        assertEquals("1989", result.first().title)
        verify(exactly = 1) { repository.getCollection() }
    }
}
