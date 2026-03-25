package com.silkfinik.vinylcatalog.domain.usecase

import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.repository.VinylRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VinylUseCasesTest {

    private lateinit var repository: VinylRepository
    private lateinit var getCollectionUseCase: GetCollectionUseCase
    private lateinit var getWishlistUseCase: GetWishlistUseCase
    private lateinit var searchDiscogsUseCase: SearchDiscogsUseCase
    private lateinit var saveRecordUseCase: SaveRecordUseCase
    private lateinit var deleteRecordUseCase: DeleteRecordUseCase
    private lateinit var getRecordDetailsUseCase: GetRecordDetailsUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        getCollectionUseCase = GetCollectionUseCase(repository)
        getWishlistUseCase = GetWishlistUseCase(repository)
        searchDiscogsUseCase = SearchDiscogsUseCase(repository)
        saveRecordUseCase = SaveRecordUseCase(repository)
        deleteRecordUseCase = DeleteRecordUseCase(repository)
        getRecordDetailsUseCase = GetRecordDetailsUseCase(repository)
    }

    @Test
    fun `GetCollectionUseCase returns data from repository`() = runTest {
        val list = listOf(VinylRecord("1", "T", "A", null, null, null, null))
        every { repository.getCollection() } returns flowOf(list)

        val result = getCollectionUseCase().first()
        assertEquals(1, result.size)
        assertEquals("1", result[0].id)
    }

    @Test
    fun `GetWishlistUseCase returns data from repository`() = runTest {
        val list = listOf(VinylRecord("2", "T2", "A2", null, null, null, null, isInWishlist = true))
        every { repository.getWishlist() } returns flowOf(list)

        val result = getWishlistUseCase().first()
        assertEquals(1, result.size)
        assertEquals("2", result[0].id)
    }

    @Test
    fun `SearchDiscogsUseCase delegates to repository`() = runTest {
        val expected = Result.success(emptyList<VinylRecord>())
        coEvery { repository.searchDiscogs("query") } returns expected

        val result = searchDiscogsUseCase("query")
        assertEquals(expected, result)
    }

    @Test
    fun `SaveRecordUseCase calls repository insertOrUpdate`() = runTest {
        val record = VinylRecord("1", "T", "A", null, null, null, null)
        saveRecordUseCase(record)
        coVerify { repository.insertOrUpdate(record) }
    }

    @Test
    fun `DeleteRecordUseCase calls repository delete`() = runTest {
        val record = VinylRecord("1", "T", "A", null, null, null, null)
        deleteRecordUseCase(record)
        coVerify { repository.delete(record) }
    }

    @Test
    fun `GetRecordDetailsUseCase returns data from repository`() = runTest {
        val record = VinylRecord("1", "T", "A", null, null, null, null)
        coEvery { repository.getRecordById("1") } returns record

        val result = getRecordDetailsUseCase("1")
        assertEquals(record, result)
    }
}
