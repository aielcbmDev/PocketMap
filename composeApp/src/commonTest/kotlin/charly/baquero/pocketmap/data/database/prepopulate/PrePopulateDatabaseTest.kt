package charly.baquero.pocketmap.data.database.prepopulate

import dev.mokkery.answering.returns
import dev.mokkery.every
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
class PrePopulateDatabaseTest {

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Verify that no interactions with the database occur if the it already exists`() =
        runTest {
            // GIVEN
            val isDatabaseCreated = true
            val prePopulateTables = mock<PrePopulateTables>() {
                everySuspend { execute() } returns Unit
            }
            val prePopulateTablesLazy = mock<Lazy<PrePopulateTables>> {
                every { value } returns prePopulateTables
            }
            val prePopulateDatabase = PrePopulateDatabase(
                isDatabaseCreated = isDatabaseCreated,
                prePopulateTablesLazy = prePopulateTablesLazy
            )

            // WHEN
            prePopulateDatabase.execute()
            runCurrent()

            // THEN
            verifyNoMoreCalls(prePopulateTables, prePopulateTablesLazy)
        }

    @Test
    fun `Verify that the database is pre-populated if it does not exist`() = runTest {
        // GIVEN
        val isDatabaseCreated = false
        val prePopulateTables = mock<PrePopulateTables>() {
            everySuspend { execute() } returns Unit
        }
        val prePopulateTablesLazy = mock<Lazy<PrePopulateTables>> {
            every { value } returns prePopulateTables
        }
        val prePopulateDatabase = PrePopulateDatabase(
            isDatabaseCreated = isDatabaseCreated,
            prePopulateTablesLazy = prePopulateTablesLazy
        )

        // WHEN
        prePopulateDatabase.execute()
        runCurrent()

        // THEN
        verifySuspend(mode = VerifyMode.exhaustiveOrder) {
            prePopulateTablesLazy.value
            prePopulateTables.execute()
        }
        verifyNoMoreCalls(prePopulateTables, prePopulateTablesLazy)
    }
}
