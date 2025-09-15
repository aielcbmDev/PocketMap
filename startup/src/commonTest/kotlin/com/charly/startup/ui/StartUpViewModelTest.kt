package com.charly.startup.ui

import com.charly.startup.domain.PrePopulateDatabaseUseCase
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifyNoMoreCalls
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class StartUpViewModelTest {

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Verify that the database is successfully pre-populated`() = runTest {
        // GIVEN
        val prePopulateDatabaseUseCase = mock<PrePopulateDatabaseUseCase>() {
            everySuspend { execute() } returns Unit
        }
        val startUpViewModel = StartUpViewModel(prePopulateDatabaseUseCase)
        assertEquals(StartUpViewState.Loading, startUpViewModel.state.value)

        // WHEN
        startUpViewModel.prePopulateDatabase()
        runCurrent()

        // THEN
        assertEquals(StartUpViewState.Success, startUpViewModel.state.value)
        verifySuspend {
            prePopulateDatabaseUseCase.execute()
        }
        verifyNoMoreCalls(prePopulateDatabaseUseCase)

    }

    @Test
    fun `Verify that the database fails to be pre-populated if an exception is thrown`() = runTest {
        // GIVEN
        val prePopulateDatabaseUseCase = mock<PrePopulateDatabaseUseCase>() {
            everySuspend { execute() } throws Exception()
        }
        val startUpViewModel = StartUpViewModel(prePopulateDatabaseUseCase)
        assertEquals(StartUpViewState.Loading, startUpViewModel.state.value)

        // WHEN
        startUpViewModel.prePopulateDatabase()
        runCurrent()

        // THEN
        assertEquals(StartUpViewState.Error, startUpViewModel.state.value)
        verifySuspend {
            prePopulateDatabaseUseCase.execute()
        }
        verifyNoMoreCalls(prePopulateDatabaseUseCase)
    }
}
