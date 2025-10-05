package com.charly.startup.ui

import com.charly.domain.usecases.prepopulate.PrePopulateDatabaseUseCase
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
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
    fun `Verify that the database is successfully pre-populated when the view model is initialized`() =
        runTest {
            // GIVEN
            val prePopulateDatabaseUseCase = mock<PrePopulateDatabaseUseCase>() {
                everySuspend { execute() } returns Unit
            }

            // WHEN
            val startUpViewModel = StartUpViewModel(prePopulateDatabaseUseCase)
            assertEquals(StartUpViewState.Loading, startUpViewModel.state.value)
            runCurrent()

            // THEN
            assertEquals(StartUpViewState.Success, startUpViewModel.state.value)
            verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
                prePopulateDatabaseUseCase.execute()
            }
        }

    @Test
    fun `Verify that the database is pre-populated twice if we call onEvent`() =
        runTest {
            // GIVEN
            val prePopulateDatabaseUseCase = mock<PrePopulateDatabaseUseCase>() {
                everySuspend { execute() } returns Unit
            }
            val startUpViewModel = StartUpViewModel(prePopulateDatabaseUseCase)
            assertEquals(StartUpViewState.Loading, startUpViewModel.state.value)

            // WHEN
            startUpViewModel.onEvent(ViewEvent.PrePopulateDatabase)
            runCurrent()

            // THEN
            assertEquals(StartUpViewState.Success, startUpViewModel.state.value)
            verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
                prePopulateDatabaseUseCase.execute()
                prePopulateDatabaseUseCase.execute()
            }
        }

    @Test
    fun `Verify that the database fails to be pre-populated if an exception is thrown when the view model is initialized`() =
        runTest {
            // GIVEN
            val prePopulateDatabaseUseCase = mock<PrePopulateDatabaseUseCase>() {
                everySuspend { execute() } throws Exception()
            }

            // WHEN
            val startUpViewModel = StartUpViewModel(prePopulateDatabaseUseCase)
            assertEquals(StartUpViewState.Loading, startUpViewModel.state.value)
            runCurrent()

            // THEN
            assertEquals(StartUpViewState.Error, startUpViewModel.state.value)
            verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
                prePopulateDatabaseUseCase.execute()
            }
        }
}
