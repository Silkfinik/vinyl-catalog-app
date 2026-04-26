package com.silkfinik.vinylcatalog.ui.screens.search

import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.usecase.SaveRecordUseCase
import com.silkfinik.vinylcatalog.domain.usecase.SearchDiscogsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val searchDiscogsUseCase = mockk<SearchDiscogsUseCase>()
    private val saveRecordUseCase = mockk<SaveRecordUseCase>()
    private lateinit var viewModel: SearchViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(searchDiscogsUseCase, saveRecordUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateQuery starts search if length greater than 2`() = runTest(testDispatcher) {
        // Arrange
        val query = "Daft Punk"
        coEvery { searchDiscogsUseCase(query, null) } returns Result.success(emptyList())

        // Act
        viewModel.updateQuery(query)
        
        // Advance time by 600ms to bypass debounce
        advanceTimeBy(600)
        runCurrent()

        // Assert
        val state = viewModel.uiState.value
        assertEquals(query, state.query)
        assertEquals(false, state.isLoading)
        coVerify(exactly = 1) { searchDiscogsUseCase(query, null) }
    }

    @Test
    fun `setFilter triggers search using updated genre filter`() = runTest(testDispatcher) {
        // Arrange
        val query = "Daft"
        val filter = "Electronic"
        coEvery { searchDiscogsUseCase(query, null) } returns Result.success(emptyList())
        coEvery { searchDiscogsUseCase(query, filter) } returns Result.success(emptyList())

        // Initial setup to have a valid query
        viewModel.updateQuery(query)
        advanceTimeBy(600)
        runCurrent()

        // Act - Changing the filter should cancel previous and instantly fire new query
        viewModel.setFilter(filter)
        runCurrent()

        // Assert
        val state = viewModel.uiState.value
        assertEquals(filter, state.activeFilter)
        coVerify(exactly = 1) { searchDiscogsUseCase(query, filter) }
    }
}
