package com.charly.domain.usecases.get

import com.charly.domain.model.Group
import com.charly.domain.repositories.get.GetAllGroupsRepository
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
class GetAllGroupsUseCaseTest {

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
        val listOfGroups = listOf(
            Group(id = 1, name = "Group 1"),
            Group(id = 2, name = "Group 2")
        )

        val getAllGroupsRepository = mock<GetAllGroupsRepository>() {
            everySuspend { execute() } returns listOfGroups
        }

        val getAllGroupsUseCase = GetAllGroupsUseCase(getAllGroupsRepository)

        // WHEN
        val result = getAllGroupsUseCase.execute()

        // THEN
        assertSame(listOfGroups, result)
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            getAllGroupsRepository.execute()
        }
    }

    @Test
    fun `Verify that execute fails`() = runTest {
        // GIVEN
        val expectedException = Exception("Error")
        val getAllGroupsRepository = mock<GetAllGroupsRepository>() {
            everySuspend { execute() } throws expectedException
        }

        val getAllGroupsUseCase = GetAllGroupsUseCase(getAllGroupsRepository)

        // WHEN
        val actualException = assertFailsWith<Exception> {
            getAllGroupsUseCase.execute()
        }

        // THEN
        assertSame(expectedException, actualException)
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            getAllGroupsRepository.execute()
        }
    }
}
