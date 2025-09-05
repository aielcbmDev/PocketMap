package charly.baquero.pocketmap.ui.startup

import charly.baquero.pocketmap.data.database.prepopulate.PrePopulateDatabase
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
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
        val prePopulateDatabase = mock<PrePopulateDatabase>() {
            everySuspend { execute() } returns Unit
        }
        val startUpViewModel = StartUpViewModel(prePopulateDatabase)
        assertEquals(StartUpViewState.Loading, startUpViewModel.state.value)

        // WHEN
        startUpViewModel.prePopulateDatabase()
        runCurrent()

        // THEN
        assertEquals(StartUpViewState.Success, startUpViewModel.state.value)

    }

    @Test
    fun `Verify that the database fails to be pre-populated`() = runTest {
        // GIVEN
        val prePopulateDatabase = mock<PrePopulateDatabase>() {
            everySuspend { execute() } throws Exception()
        }
        val startUpViewModel = StartUpViewModel(prePopulateDatabase)
        assertEquals(StartUpViewState.Loading, startUpViewModel.state.value)

        // WHEN
        startUpViewModel.prePopulateDatabase()
        runCurrent()

        // THEN
        assertEquals(StartUpViewState.Error, startUpViewModel.state.value)
    }
}
