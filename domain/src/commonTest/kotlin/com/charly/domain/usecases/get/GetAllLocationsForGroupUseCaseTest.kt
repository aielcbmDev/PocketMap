package com.charly.domain.usecases.get

import com.charly.domain.model.Location
import com.charly.domain.repositories.get.GetAllLocationsForGroupRepository
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
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllLocationsForGroupUseCaseTest {

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
        val listOfLocations = listOf(
            Location(
                id = 1,
                title = "Location 1",
                description = "Description 1",
                latitude = 1.0,
                longitude = 1.0
            ),
            Location(
                id = 2,
                title = "Location 2",
                description = "Description 2",
                latitude = 2.0,
                longitude = 2.0
            )
        )
        val idGroup = 3L
        val getAllLocationsForGroupRepository = mock<GetAllLocationsForGroupRepository> {
            everySuspend { execute(idGroup) } returns flowOf(listOfLocations)
        }

        val getAllLocationsForGroupUseCase =
            GetAllLocationsForGroupUseCase(getAllLocationsForGroupRepository)

        // WHEN
        val result = getAllLocationsForGroupUseCase.execute(idGroup).first()

        // THEN
        assertSame(listOfLocations, result)
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            @Suppress("UnusedFlow")
            getAllLocationsForGroupRepository.execute(idGroup)
        }
    }

    @Test
    fun `Verify that execute fails`() = runTest {
        // GIVEN
        val expectedException = Exception("Error")
        val idGroup = 3L
        val getAllLocationsForGroupRepository = mock<GetAllLocationsForGroupRepository> {
            everySuspend { execute(idGroup) } returns flow { throw expectedException }
        }
        val getAllLocationsForGroupUseCase =
            GetAllLocationsForGroupUseCase(getAllLocationsForGroupRepository)

        // WHEN
        val actualException = assertFailsWith<Exception> {
            getAllLocationsForGroupUseCase.execute(idGroup).collect()
        }

        // THEN
        assertSame(expectedException, actualException)
        verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
            @Suppress("UnusedFlow")
            getAllLocationsForGroupRepository.execute(idGroup)
        }
    }
}
