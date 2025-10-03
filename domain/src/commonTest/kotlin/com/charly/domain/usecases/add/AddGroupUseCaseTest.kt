package com.charly.domain.usecases.add

import com.charly.domain.repositories.add.AddGroupRepository
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
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
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

@OptIn(ExperimentalCoroutinesApi::class)
class AddGroupUseCaseTest {

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
        val groupName = "Group Name"
        val addGroupRepository = mock<AddGroupRepository>() {
            everySuspend { execute(groupName) } returns Unit
        }

        val addGroupUseCase = AddGroupUseCase(addGroupRepository)

        // WHEN
        addGroupUseCase.execute(groupName)
        runCurrent()

        // THEN
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            addGroupRepository.execute(groupName)
        }
        verifyNoMoreCalls(addGroupRepository)
    }

    @Test
    fun `Verify that execute fails`() = runTest {
        // GIVEN
        val expectedException = Exception("Error")
        val groupName = "Group Name"
        val addGroupRepository = mock<AddGroupRepository>() {
            everySuspend { execute(groupName) } throws expectedException
        }
        val addGroupUseCase = AddGroupUseCase(addGroupRepository)

        // WHEN
        val actualException = assertFailsWith<Exception> {
            addGroupUseCase.execute(groupName)
            runCurrent()
        }

        // THEN
        assertSame(expectedException, actualException)
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            addGroupRepository.execute(groupName)
        }
        verifyNoMoreCalls(addGroupRepository)
    }
}
