package com.charly.domain.usecases.edit

import com.charly.domain.repositories.edit.EditGroupRepository
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
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

@OptIn(ExperimentalCoroutinesApi::class)
class EditGroupUseCaseTest {

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
        val id = 1L
        val groupName = "Group 1"
        val editGroupRepository = mock<EditGroupRepository> {
            everySuspend { execute(id, groupName) } returns Unit
        }
        val editGroupUseCase = EditGroupUseCase(editGroupRepository)

        // WHEN
        editGroupUseCase.execute(id, groupName)

        // THEN
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            editGroupRepository.execute(id, groupName)
        }
    }

    @Test
    fun `Verify that execute fails`() = runTest {
        // GIVEN
        val expectedException = Exception("Error")
        val id = 1L
        val groupName = "Group 1"
        val editGroupRepository = mock<EditGroupRepository> {
            everySuspend { execute(id, groupName) } throws expectedException
        }
        val editGroupUseCase = EditGroupUseCase(editGroupRepository)

        // WHEN
        val actualException = assertFailsWith<Exception> {
            editGroupUseCase.execute(id, groupName)
        }

        // THEN
        assertSame(expectedException, actualException)
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            editGroupRepository.execute(id, groupName)
        }
    }
}
