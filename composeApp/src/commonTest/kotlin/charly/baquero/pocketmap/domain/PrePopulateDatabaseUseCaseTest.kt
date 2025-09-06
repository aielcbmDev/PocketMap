package charly.baquero.pocketmap.domain

import charly.baquero.pocketmap.data.database.prepopulate.PrePopulateDatabase
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
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

@OptIn(ExperimentalCoroutinesApi::class)
class PrePopulateDatabaseUseCaseTest {

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Verify that execute succeeds`() = runTest {
        // GIVEN
        val prePopulateDatabase = mock<PrePopulateDatabase>() {
            everySuspend { execute() } returns Unit
        }
        val prePopulateDatabaseUseCase = PrePopulateDatabaseUseCase(prePopulateDatabase)

        // WHEN
        prePopulateDatabaseUseCase.execute()
        runCurrent()

        // THEN
        verifySuspend(mode = VerifyMode.exhaustiveOrder) {
            prePopulateDatabase.execute()
        }
        verifyNoMoreCalls(prePopulateDatabase)
    }
}
