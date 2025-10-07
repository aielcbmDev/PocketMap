package com.charly.domain.usecases.get

import com.charly.domain.model.Group
import com.charly.domain.repositories.get.GetAllGroupsRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
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

        val getAllGroupsRepository = mock<GetAllGroupsRepository> {
            everySuspend { execute() } returns flowOf(listOfGroups)
        }

        val getAllGroupsUseCase = GetAllGroupsUseCase(getAllGroupsRepository)

        // WHEN
        val result = getAllGroupsUseCase.execute().first()

        // THEN
        assertEquals(listOfGroups, result)
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            @Suppress("UnusedFlow")
            getAllGroupsRepository.execute()
        }
    }

    @Test
    fun `Verify that execute fails`() = runTest {
        // GIVEN
        val expectedException = Exception("Error")
        val getAllGroupsRepository = mock<GetAllGroupsRepository> {
            everySuspend { execute() } returns flow { throw expectedException }
        }

        val getAllGroupsUseCase = GetAllGroupsUseCase(getAllGroupsRepository)

        // WHEN
        val actualException = assertFailsWith<Exception> {
            getAllGroupsUseCase.execute().collect()
        }

        // THEN
        assertSame(expectedException, actualException)
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            @Suppress("UnusedFlow")
            getAllGroupsRepository.execute()
        }
    }
}
