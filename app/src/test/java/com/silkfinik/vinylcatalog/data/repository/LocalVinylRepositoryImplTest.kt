package com.silkfinik.vinylcatalog.data.repository

import com.silkfinik.vinylcatalog.data.local.dao.VinylRecordDao
import com.silkfinik.vinylcatalog.data.local.entity.VinylRecordEntity
import com.silkfinik.vinylcatalog.data.remote.DiscogsApi
import com.silkfinik.vinylcatalog.data.remote.dto.DiscogsReleaseDto
import com.silkfinik.vinylcatalog.data.remote.dto.DiscogsSearchResponse
import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LocalVinylRepositoryImplTest {

    private lateinit var dao: VinylRecordDao
    private lateinit var api: DiscogsApi
    private lateinit var repository: LocalVinylRepositoryImpl

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        api = mockk()
        repository = LocalVinylRepositoryImpl(dao, api)
    }

    @Test
    fun `getCollection maps flow from dao`() = runTest {
        val entity = VinylRecordEntity("1", "Title", "Artist", null, null, null, null)
        every { dao.getCollection() } returns flowOf(listOf(entity))

        val result = repository.getCollection().first()

        assertEquals(1, result.size)
        assertEquals("1", result[0].id)
        assertEquals("Title", result[0].title)
    }

    @Test
    fun `searchDiscogs returns success result on api success`() = runTest {
        val dto = DiscogsReleaseDto(1L, "A - T")
        coEvery { api.searchReleases("query", any(), any()) } returns DiscogsSearchResponse(listOf(dto))

        val result = repository.searchDiscogs("query")

        assertTrue(result.isSuccess)
        val list = result.getOrNull()!!
        assertEquals(1, list.size)
        assertEquals("1", list[0].id)
        assertEquals("A", list[0].artist)
        assertEquals("T", list[0].title)
    }

    @Test
    fun `searchDiscogs returns failure on api error`() = runTest {
        coEvery { api.searchReleases(any(), any(), any()) } throws RuntimeException("Network error")

        val result = repository.searchDiscogs("error")

        assertTrue(result.isFailure)
    }
    
    @Test
    fun `insertOrUpdate calls dao`() = runTest {
        val record = VinylRecord("1", "T", "A", null, null, null, null)
        repository.insertOrUpdate(record)
        coVerify { dao.insertOrUpdate(any()) }
    }
}
