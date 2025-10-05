package com.charly.domain.usecases.prepopulate

import com.charly.domain.repositories.prepopulate.PrePopulateDatabaseRepository
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
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

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
        val prePopulateDatabaseRepository = mock<PrePopulateDatabaseRepository>() {
            everySuspend { execute() } returns Unit
        }
        val prePopulateDatabaseUseCase = PrePopulateDatabaseUseCase(prePopulateDatabaseRepository)

        // WHEN
        prePopulateDatabaseUseCase.execute()
        runCurrent()

        // THEN
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            prePopulateDatabaseRepository.execute()
        }
    }

    @Test
    fun `Verify that execute fails`() = runTest {
        // GIVEN
        val expectedException = Exception("Error")
        val prePopulateDatabaseRepository = mock<PrePopulateDatabaseRepository>() {
            everySuspend { execute() } throws expectedException
        }
        val prePopulateDatabaseUseCase = PrePopulateDatabaseUseCase(prePopulateDatabaseRepository)

        // WHEN
        val actualException = assertFailsWith<Exception> {
            prePopulateDatabaseUseCase.execute()
            runCurrent()
        }

        // THEN
        assertSame(expectedException, actualException)
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            prePopulateDatabaseRepository.execute()
        }
    }
}
