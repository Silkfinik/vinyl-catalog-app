package com.silkfinik.vinylcatalog.ui.screens.collection

import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.usecase.DeleteRecordUseCase
import com.silkfinik.vinylcatalog.domain.usecase.GetCollectionUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MyCollectionViewModelTest {

    private val getCollectionUseCase = mockk<GetCollectionUseCase>()
    private val deleteRecordUseCase = mockk<DeleteRecordUseCase>()
    private lateinit var viewModel: MyCollectionViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val mockRecords = listOf(
        VinylRecord("1", "Zonkey", "Umphrey's McGee", null, "2016", null, "Rock", "LP", 0, null, false),
        VinylRecord("2", "Aja", "Steely Dan", null, "1977", null, "Jazz-Rock", "LP", 0, null, false)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { getCollectionUseCase() } returns flowOf(mockRecords)
        viewModel = MyCollectionViewModel(getCollectionUseCase, deleteRecordUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewModel exposes collection sorted by title when order changed to Title`() = runTest(testDispatcher) {
        // StateFlows with WhileSubscribed need a collector to materialize
        val collectJob = launch { viewModel.uiState.collect() }
        
        // Assert Initial State
        runCurrent()
        var state = viewModel.uiState.value
        assertEquals("Zonkey", state.records.first().title) // DateAdded preserves original mock order
        
        // Act - sort by Title
        viewModel.setSortOrder(MyCollectionViewModel.SortOrder.Title)
        runCurrent()
        
        // Assert sorted State (Aja < Zonkey)
        state = viewModel.uiState.value
        assertEquals("Aja", state.records.first().title)
        
        collectJob.cancel()
    }

    @Test
    fun `deleteRecord delegates to useCase`() = runTest(testDispatcher) {
        // Arrange
        val record = mockRecords.first()
        io.mockk.coEvery { deleteRecordUseCase(record) } returns Unit

        // Act
        viewModel.deleteRecord(record)
        runCurrent()

        // Assert
        coVerify(exactly = 1) { deleteRecordUseCase(record) }
    }
}
