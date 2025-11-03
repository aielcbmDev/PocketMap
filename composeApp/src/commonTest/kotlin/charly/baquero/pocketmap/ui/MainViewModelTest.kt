package charly.baquero.pocketmap.ui

import charly.baquero.pocketmap.ui.model.GroupModel
import charly.baquero.pocketmap.ui.model.LocationModel
import com.charly.domain.model.database.Group
import com.charly.domain.model.database.Location
import com.charly.domain.usecases.database.add.AddGroupUseCase
import com.charly.domain.usecases.database.delete.DeleteGroupsUseCase
import com.charly.domain.usecases.database.edit.EditGroupUseCase
import com.charly.domain.usecases.database.get.GetAllGroupsUseCase
import com.charly.domain.usecases.database.get.GetAllLocationsForGroupUseCase
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Verify that ClearMap intent generates the correct UI state`() {
        // GIVEN
        val mainViewModel = MainViewModel(
            getAllGroupsUseCase = mock(),
            getAllLocationsForGroupUseCase = mock(),
            addGroupUseCase = mock(),
            deleteGroupsUseCase = mock(),
            editGroupUseCase = mock()
        )

        // WHEN
        mainViewModel.onEvent(ViewEvent.ClearMap)

        // THEN
        assertIs<LocationsViewState.NoGroupSelected>(mainViewModel.state.value.locationsViewState)
    }

    @Test
    fun `Verify that LocationClick intent generates an Error if the initial locations view state is not Success`() {
        // GIVEN
        val location = LocationModel(
            id = 1,
            title = "Location 1",
            description = "Description 1",
            latitude = 1.0,
            longitude = 1.0
        )
        val mainViewModel = MainViewModel(
            getAllGroupsUseCase = mock(),
            getAllLocationsForGroupUseCase = mock(),
            addGroupUseCase = mock(),
            deleteGroupsUseCase = mock(),
            editGroupUseCase = mock()
        )

        // WHEN
        mainViewModel.onEvent(ViewEvent.LocationClick(location))

        // THEN
        assertIs<LocationsViewState.Error>(mainViewModel.state.value.locationsViewState)
    }

    @Test
    fun `Verify that LocationClick intent generates the correct UI state`() = runTest {
        // GIVEN
        val mainViewModel = fetchLocationsForGroup(this)
        val locationModelClicked = LocationModel(
            id = 1,
            title = "Location 1",
            description = "Description 1",
            latitude = 1.0,
            longitude = 1.0
        )

        // WHEN
        mainViewModel.onEvent(ViewEvent.LocationClick(locationModelClicked))

        // THEN
        val locationsViewState = mainViewModel.state.value.locationsViewState
        assertIs<LocationsViewState.Success>(locationsViewState)
        assertEquals(locationModelClicked, locationsViewState.locationSelected)
    }

    @Test
    fun `Verify that if FetchAllGroups intent result is successful the correct UI state is generated`() =
        runTest {
            // GIVEN
            val expectedList = listOf(
                Group(1, "Group 1"),
                Group(2, "Group 2"),
            )
            val getAllGroupsUseCase = mock<GetAllGroupsUseCase> {
                everySuspend { execute() } returns flowOf(expectedList)
            }

            // WHEN
            val mainViewModel = MainViewModel(
                getAllGroupsUseCase = getAllGroupsUseCase,
                getAllLocationsForGroupUseCase = mock(),
                addGroupUseCase = mock(),
                deleteGroupsUseCase = mock(),
                editGroupUseCase = mock()
            )
            assertIs<GroupViewState.Loading>(mainViewModel.state.value.groupViewState)
            runCurrent()

            // THEN
            val mainUiState = mainViewModel.state.value
            assertIs<GroupViewState.Success>(mainUiState.groupViewState)
            verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
                @Suppress("UnusedFlow")
                getAllGroupsUseCase.execute()
            }
            val groupList = mainUiState.groupViewState.groupList
            assertEquals(2, groupList.size)
            val groupModel0 = groupList[0]
            val groupModel1 = groupList[1]
            assertIs<GroupModel>(groupModel0)
            assertIs<GroupModel>(groupModel1)
            assertEquals(expectedList[0].id, groupModel0.id)
            assertEquals(expectedList[0].name, groupModel0.name)
            assertEquals(expectedList[1].id, groupModel1.id)
            assertEquals(expectedList[1].name, groupModel1.name)
        }

    @Test
    fun `Verify that if FetchAllGroups intent result is empty the correct UI state is generated`() =
        runTest {
            // GIVEN
            val getAllGroupsUseCase = mock<GetAllGroupsUseCase> {
                everySuspend { execute() } returns flowOf(emptyList())
            }

            // WHEN
            val mainViewModel = MainViewModel(
                getAllGroupsUseCase = getAllGroupsUseCase,
                getAllLocationsForGroupUseCase = mock(),
                addGroupUseCase = mock(),
                deleteGroupsUseCase = mock(),
                editGroupUseCase = mock()
            )
            assertIs<GroupViewState.Loading>(mainViewModel.state.value.groupViewState)
            runCurrent()

            // THEN
            assertIs<GroupViewState.Empty>(mainViewModel.state.value.groupViewState)
            verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
                @Suppress("UnusedFlow")
                getAllGroupsUseCase.execute()
            }
        }

    @Test
    fun `Verify that if FetchAllGroups intent throws an exception the correct UI state is generated`() =
        runTest {
            // GIVEN
            val getAllGroupsUseCase = mock<GetAllGroupsUseCase> {
                everySuspend { execute() } throws Exception("Exception")
            }

            // WHEN
            val mainViewModel = MainViewModel(
                getAllGroupsUseCase = getAllGroupsUseCase,
                getAllLocationsForGroupUseCase = mock(),
                addGroupUseCase = mock(),
                deleteGroupsUseCase = mock(),
                editGroupUseCase = mock()
            )
            assertIs<GroupViewState.Loading>(mainViewModel.state.value.groupViewState)
            runCurrent()

            // THEN
            assertIs<GroupViewState.Error>(mainViewModel.state.value.groupViewState)
            verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
                @Suppress("UnusedFlow")
                getAllGroupsUseCase.execute()
            }
        }

    @Test
    fun `Verify that if FetchLocationsForGroup intent result is successful the correct UI state is generated`() =
        runTest {
            // GIVEN
            val expectedLocationList = mutableListOf(
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
            val groupModel = GroupModel(
                id = 1,
                name = "Group 1"
            )
            val getAllLocationsForGroupUseCase = mock<GetAllLocationsForGroupUseCase> {
                everySuspend { execute(groupModel.id) } returns flowOf(expectedLocationList)
            }
            val mainViewModel = MainViewModel(
                getAllGroupsUseCase = mock(),
                getAllLocationsForGroupUseCase = getAllLocationsForGroupUseCase,
                addGroupUseCase = mock(),
                deleteGroupsUseCase = mock(),
                editGroupUseCase = mock()
            )

            // WHEN
            mainViewModel.onEvent(ViewEvent.FetchLocationsForGroup(groupModel))
            assertIs<LocationsViewState.Loading>(mainViewModel.state.value.locationsViewState)
            assertEquals(
                groupModel.name,
                (mainViewModel.state.value.locationsViewState as LocationsViewState.Loading).groupName
            )
            runCurrent()

            // THEN
            val locationsViewState = mainViewModel.state.value.locationsViewState
            assertIs<LocationsViewState.Success>(locationsViewState)
            verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
                @Suppress("UnusedFlow")
                getAllLocationsForGroupUseCase.execute(groupModel.id)
            }
            assertEquals(groupModel.name, locationsViewState.groupName)
            val locationModelList = locationsViewState.locationList
            assertEquals(2, locationModelList.size)
            val location0 = expectedLocationList[0]
            val location1 = expectedLocationList[1]
            val locationModel0 = locationModelList[0]
            val locationModel1 = locationModelList[1]
            assertIs<LocationModel>(locationModel0)
            assertIs<LocationModel>(locationModel1)
            assertEquals(location0.id, locationModel0.id)
            assertEquals(location0.title, locationModel0.title)
            assertEquals(location0.description, locationModel0.description)
            assertEquals(location0.latitude, locationModel0.latitude)
            assertEquals(location0.longitude, locationModel0.longitude)
            assertEquals(location1.id, locationModel1.id)
            assertEquals(location1.title, locationModel1.title)
            assertEquals(location1.description, locationModel1.description)
            assertEquals(location1.latitude, locationModel1.latitude)
            assertEquals(location1.longitude, locationModel1.longitude)
        }

    @Test
    fun `Verify that if FetchLocationsForGroup intent result is empty the correct UI state is generated`() =
        runTest {
            // GIVEN
            val groupModel = GroupModel(
                id = 1,
                name = "Group 1"
            )
            val getAllLocationsForGroupUseCase = mock<GetAllLocationsForGroupUseCase> {
                everySuspend { execute(groupModel.id) } returns flowOf(emptyList())
            }
            val mainViewModel = MainViewModel(
                getAllGroupsUseCase = mock(),
                getAllLocationsForGroupUseCase = getAllLocationsForGroupUseCase,
                addGroupUseCase = mock(),
                deleteGroupsUseCase = mock(),
                editGroupUseCase = mock()
            )

            // WHEN
            mainViewModel.onEvent(ViewEvent.FetchLocationsForGroup(groupModel))
            assertIs<LocationsViewState.Loading>(mainViewModel.state.value.locationsViewState)
            assertEquals(
                groupModel.name,
                (mainViewModel.state.value.locationsViewState as LocationsViewState.Loading).groupName
            )
            runCurrent()

            // THEN
            val locationsViewState = mainViewModel.state.value.locationsViewState
            assertIs<LocationsViewState.Empty>(locationsViewState)
            verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
                @Suppress("UnusedFlow")
                getAllLocationsForGroupUseCase.execute(groupModel.id)
            }
            assertEquals(groupModel.name, locationsViewState.groupName)
        }

    @Test
    fun `Verify that if FetchLocationsForGroup intent throws an exception the correct UI state is generated`() =
        runTest {
            // GIVEN
            val groupModel = GroupModel(
                id = 1,
                name = "Group 1"
            )
            val getAllLocationsForGroupUseCase = mock<GetAllLocationsForGroupUseCase> {
                everySuspend { execute(groupModel.id) } throws Exception("Exception")
            }
            val mainViewModel = MainViewModel(
                getAllGroupsUseCase = mock(),
                getAllLocationsForGroupUseCase = getAllLocationsForGroupUseCase,
                addGroupUseCase = mock(),
                deleteGroupsUseCase = mock(),
                editGroupUseCase = mock()
            )

            // WHEN
            mainViewModel.onEvent(ViewEvent.FetchLocationsForGroup(groupModel))
            assertIs<LocationsViewState.Loading>(mainViewModel.state.value.locationsViewState)
            assertEquals(
                groupModel.name,
                (mainViewModel.state.value.locationsViewState as LocationsViewState.Loading).groupName
            )
            runCurrent()

            // THEN
            val locationsViewState = mainViewModel.state.value.locationsViewState
            assertIs<LocationsViewState.Error>(locationsViewState)
            verifySuspend(mode = VerifyMode.Companion.exhaustiveOrder) {
                @Suppress("UnusedFlow")
                getAllLocationsForGroupUseCase.execute(groupModel.id)
            }
        }

    @Test
    fun `Verify that DisplayGroupOptionsMenu intent generates an Error if the initial groups view state is not Success`() =
        runTest {
            // GIVEN
            val mainViewModel = MainViewModel(
                getAllGroupsUseCase = mock(),
                getAllLocationsForGroupUseCase = mock(),
                addGroupUseCase = mock(),
                deleteGroupsUseCase = mock(),
                editGroupUseCase = mock()
            )
            val groupModel = GroupModel(
                id = 1,
                name = "Group 1"
            )

            // WHEN
            mainViewModel.onEvent(ViewEvent.DisplayGroupOptionsMenu(groupModel))

            // THEN
            val groupViewState = mainViewModel.state.value.groupViewState
            assertIs<GroupViewState.Error>(groupViewState)
        }

    @Test
    fun `Verify that DisplayGroupOptionsMenu intent generates the correct UI state`() = runTest {
        // GIVEN
        val mainViewModel = fetchAllGroups(this)
        val groupModel = GroupModel(
            id = 1,
            name = "Group 1"
        )

        // WHEN
        mainViewModel.onEvent(ViewEvent.DisplayGroupOptionsMenu(groupModel))

        // THEN
        val groupViewState = mainViewModel.state.value.groupViewState
        assertIs<GroupViewState.Success>(groupViewState)
        assertEquals(1, groupViewState.selectedGroups.size)
        assertTrue(groupViewState.selectedGroups.contains(groupModel.id))
    }

    @Test
    fun `Verify that DismissGroupOptionsMenu intent generates an Error if the initial groups view state is not Success`() =
        runTest {
            // GIVEN
            val mainViewModel = MainViewModel(
                getAllGroupsUseCase = mock(),
                getAllLocationsForGroupUseCase = mock(),
                addGroupUseCase = mock(),
                deleteGroupsUseCase = mock(),
                editGroupUseCase = mock()
            )

            // WHEN
            mainViewModel.onEvent(ViewEvent.DismissGroupOptionsMenu)

            // THEN
            val groupViewState = mainViewModel.state.value.groupViewState
            assertIs<GroupViewState.Error>(groupViewState)
        }

    @Test
    fun `Verify that DismissGroupOptionsMenu intent generates the correct UI state`() = runTest {
        // GIVEN
        val mainViewModel = fetchAllGroups(this)

        // WHEN
        mainViewModel.onEvent(ViewEvent.DismissGroupOptionsMenu)

        // THEN
        val groupViewState = mainViewModel.state.value.groupViewState
        assertIs<GroupViewState.Success>(groupViewState)
        assertTrue(groupViewState.selectedGroups.isEmpty())
    }

    @Test
    fun `Verify that ShowCreateGroupDialog intent generates the correct UI state`() = runTest {
        // GIVEN
        val mainViewModel = MainViewModel(
            getAllGroupsUseCase = mock(),
            getAllLocationsForGroupUseCase = mock(),
            addGroupUseCase = mock(),
            deleteGroupsUseCase = mock(),
            editGroupUseCase = mock()
        )

        // WHEN
        mainViewModel.onEvent(ViewEvent.ShowCreateGroupDialog)

        // THEN
        val dialogState = mainViewModel.state.value.dialogState
        assertIs<DialogState.CreateGroup>(dialogState)
    }

    @Test
    fun `Verify that ShowDeleteGroupsDialog intent generates the correct UI state`() = runTest {
        // GIVEN
        val mainViewModel = MainViewModel(
            getAllGroupsUseCase = mock(),
            getAllLocationsForGroupUseCase = mock(),
            addGroupUseCase = mock(),
            deleteGroupsUseCase = mock(),
            editGroupUseCase = mock()
        )

        // WHEN
        mainViewModel.onEvent(ViewEvent.ShowDeleteGroupsDialog)

        // THEN
        val dialogState = mainViewModel.state.value.dialogState
        assertIs<DialogState.DeleteGroups>(dialogState)
    }

    @Test
    fun `Verify that ShowEditGroupDialog intent generates the correct UI state`() = runTest {
        // GIVEN
        val mainViewModel = MainViewModel(
            getAllGroupsUseCase = mock(),
            getAllLocationsForGroupUseCase = mock(),
            addGroupUseCase = mock(),
            deleteGroupsUseCase = mock(),
            editGroupUseCase = mock()
        )

        // WHEN
        mainViewModel.onEvent(ViewEvent.ShowEditGroupDialog)

        // THEN
        val dialogState = mainViewModel.state.value.dialogState
        assertIs<DialogState.EditGroup>(dialogState)
    }

    @Test
    fun `Verify that DismissDialog intent generates the correct UI state`() = runTest {
        // GIVEN
        val mainViewModel = MainViewModel(
            getAllGroupsUseCase = mock(),
            getAllLocationsForGroupUseCase = mock(),
            addGroupUseCase = mock(),
            deleteGroupsUseCase = mock(),
            editGroupUseCase = mock()
        )

        // WHEN
        mainViewModel.onEvent(ViewEvent.DismissDialog)

        // THEN
        val dialogState = mainViewModel.state.value.dialogState
        assertIs<DialogState.NoDialog>(dialogState)
    }

    @Test
    fun `Verify that if CreateGroup intent fails it generates the correct UI state`() = runTest {
        // GIVEN
        val groupName = "a random group name"
        val addGroupUseCase = mock<AddGroupUseCase> {
            everySuspend { execute(groupName) } throws Exception("Exception")
        }
        val mainViewModel = MainViewModel(
            getAllGroupsUseCase = mock(),
            getAllLocationsForGroupUseCase = mock(),
            addGroupUseCase = addGroupUseCase,
            deleteGroupsUseCase = mock(),
            editGroupUseCase = mock()
        )

        // WHEN
        mainViewModel.onEvent(ViewEvent.CreateGroup(groupName))
        runCurrent()

        // THEN
        val dialogState = mainViewModel.state.value.dialogState
        assertIs<DialogState.CreateGroup>(dialogState)
        assertTrue(dialogState.displayError)
    }

    @Test
    fun `Verify that if CreateGroup intent succeeds it generates the correct UI state`() = runTest {
        // GIVEN
        val groupName = "a random group name"
        val addGroupUseCase = mock<AddGroupUseCase> {
            everySuspend { execute(groupName) } returns Unit
        }
        val mainViewModel = MainViewModel(
            getAllGroupsUseCase = mock(),
            getAllLocationsForGroupUseCase = mock(),
            addGroupUseCase = addGroupUseCase,
            deleteGroupsUseCase = mock(),
            editGroupUseCase = mock()
        )

        // WHEN
        mainViewModel.onEvent(ViewEvent.CreateGroup(groupName))
        runCurrent()

        // THEN
        val dialogState = mainViewModel.state.value.dialogState
        assertIs<DialogState.NoDialog>(dialogState)
    }

    @Test
    fun `Verify that DeleteGroups intent fails if the initial UI state is not GroupViewState Success`() =
        runTest {
            // GIVEN
            val mainViewModel = MainViewModel(
                getAllGroupsUseCase = mock(),
                getAllLocationsForGroupUseCase = mock(),
                addGroupUseCase = mock(),
                deleteGroupsUseCase = mock(),
                editGroupUseCase = mock()
            )

            // WHEN
            mainViewModel.onEvent(ViewEvent.DeleteGroups)

            // THEN
            val groupViewState = mainViewModel.state.value.groupViewState
            assertIs<GroupViewState.Error>(groupViewState)
        }

    @Test
    fun `Verify that DeleteGroups intent fails if there are no selected groups`() = runTest {
        // GIVEN
        val deleteGroupsUseCase = mock<DeleteGroupsUseCase> {
            everySuspend { execute(emptySet()) } returns Unit
        }
        val mainViewModel = fetchAllGroups(
            testScope = this,
            deleteGroupsUseCase = deleteGroupsUseCase
        )

        // WHEN
        mainViewModel.onEvent(ViewEvent.DeleteGroups)

        // THEN
        val groupViewState = mainViewModel.state.value.groupViewState
        assertIs<GroupViewState.Success>(groupViewState)
        assertIs<DeleteGroupState.Error>(groupViewState.deleteGroupState)
    }

    @Test
    fun `Verify that DeleteGroups intent succeeds if there are is at least one selected group`() =
        runTest {
            // GIVEN
            val groupModel = GroupModel(
                id = 1,
                name = "Group 1"
            )
            val deleteGroupsUseCase = mock<DeleteGroupsUseCase> {
                everySuspend { execute(setOf(groupModel.id)) } returns Unit
            }
            val mainViewModel = fetchAllGroups(
                testScope = this,
                deleteGroupsUseCase = deleteGroupsUseCase
            )
            mainViewModel.onEvent(ViewEvent.DisplayGroupOptionsMenu(groupModel))

            // WHEN
            mainViewModel.onEvent(ViewEvent.DeleteGroups)
            assertIs<GroupViewState.Success>(mainViewModel.state.value.groupViewState)
            assertIs<DeleteGroupState.Loading>((mainViewModel.state.value.groupViewState as GroupViewState.Success).deleteGroupState)
            runCurrent()

            // THEN
            val mainUiState = mainViewModel.state.value
            val groupViewState = mainUiState.groupViewState
            val dialogState = mainUiState.dialogState
            assertIs<GroupViewState.Success>(groupViewState)
            assertIs<DialogState.NoDialog>(dialogState)
            assertIs<DeleteGroupState.Success>(groupViewState.deleteGroupState)
        }

    @Test
    fun `Verify that DeleteGroups intent fails if an exception is thrown`() = runTest {
        // GIVEN
        val groupModel = GroupModel(
            id = 1,
            name = "Group 1"
        )
        val deleteGroupsUseCase = mock<DeleteGroupsUseCase> {
            everySuspend { execute(setOf(groupModel.id)) } throws Exception("Exception")
        }
        val mainViewModel = fetchAllGroups(
            testScope = this,
            deleteGroupsUseCase = deleteGroupsUseCase
        )
        mainViewModel.onEvent(ViewEvent.DisplayGroupOptionsMenu(groupModel))

        // WHEN
        mainViewModel.onEvent(ViewEvent.DeleteGroups)
        assertIs<GroupViewState.Success>(mainViewModel.state.value.groupViewState)
        assertIs<DeleteGroupState.Loading>((mainViewModel.state.value.groupViewState as GroupViewState.Success).deleteGroupState)
        runCurrent()

        // THEN
        val mainUiState = mainViewModel.state.value
        val groupViewState = mainUiState.groupViewState
        assertIs<GroupViewState.Success>(groupViewState)
        assertIs<DeleteGroupState.Error>(groupViewState.deleteGroupState)
    }

    @Test
    fun `Verify that EditGroup intent fails if the initial UI state is not GroupViewState Success`() =
        runTest {
            // GIVEN
            val mainViewModel = MainViewModel(
                getAllGroupsUseCase = mock(),
                getAllLocationsForGroupUseCase = mock(),
                addGroupUseCase = mock(),
                deleteGroupsUseCase = mock(),
                editGroupUseCase = mock()
            )
            val groupName = "a random name"

            // WHEN
            mainViewModel.onEvent(ViewEvent.EditGroup(groupName))

            // THEN
            val groupViewState = mainViewModel.state.value.groupViewState
            assertIs<GroupViewState.Error>(groupViewState)
        }

    @Test
    fun `Verify that EditGroup intent fails if there is no selected group`() = runTest {
        // GIVEN
        val deleteGroupsUseCase = mock<DeleteGroupsUseCase> {
            everySuspend { execute(emptySet()) } returns Unit
        }
        val mainViewModel = fetchAllGroups(
            testScope = this,
            deleteGroupsUseCase = deleteGroupsUseCase
        )
        val groupName = "a random name"

        // WHEN
        mainViewModel.onEvent(ViewEvent.EditGroup(groupName))

        // THEN
        val groupViewState = mainViewModel.state.value.groupViewState
        assertIs<GroupViewState.Success>(groupViewState)
        assertIs<EditGroupState.Error>(groupViewState.editGroupState)
    }

    @Test
    fun `Verify that EditGroup intent succeeds if there are is one selected group`() =
        runTest {
            // GIVEN
            val groupModel = GroupModel(
                id = 1,
                name = "Group 1"
            )
            val editGroupUseCase = mock<EditGroupUseCase> {
                everySuspend { execute(groupModel.id, groupModel.name) } returns Unit
            }
            val mainViewModel = fetchAllGroups(
                testScope = this,
                editGroupUseCase = editGroupUseCase
            )
            mainViewModel.onEvent(ViewEvent.DisplayGroupOptionsMenu(groupModel))

            // WHEN
            mainViewModel.onEvent(ViewEvent.EditGroup(groupModel.name))
            assertIs<GroupViewState.Success>(mainViewModel.state.value.groupViewState)
            assertIs<EditGroupState.Loading>((mainViewModel.state.value.groupViewState as GroupViewState.Success).editGroupState)
            runCurrent()

            // THEN
            val mainUiState = mainViewModel.state.value
            val groupViewState = mainUiState.groupViewState
            val dialogState = mainUiState.dialogState
            assertIs<GroupViewState.Success>(groupViewState)
            assertIs<DialogState.NoDialog>(dialogState)
            assertIs<EditGroupState.Success>(groupViewState.editGroupState)
        }

    @Test
    fun `Verify that EditGroup intent fails if an exception is thrown`() = runTest {
        // GIVEN
        val groupModel = GroupModel(
            id = 1,
            name = "Group 1"
        )
        val editGroupUseCase = mock<EditGroupUseCase> {
            everySuspend { execute(groupModel.id, groupModel.name) } throws Exception("Exception")
        }
        val mainViewModel = fetchAllGroups(
            testScope = this,
            editGroupUseCase = editGroupUseCase
        )
        mainViewModel.onEvent(ViewEvent.DisplayGroupOptionsMenu(groupModel))

        // WHEN
        mainViewModel.onEvent(ViewEvent.EditGroup(groupModel.name))
        assertIs<GroupViewState.Success>(mainViewModel.state.value.groupViewState)
        assertIs<EditGroupState.Loading>((mainViewModel.state.value.groupViewState as GroupViewState.Success).editGroupState)
        runCurrent()

        // THEN
        val mainUiState = mainViewModel.state.value
        val groupViewState = mainUiState.groupViewState
        assertIs<GroupViewState.Success>(groupViewState)
        assertIs<EditGroupState.Error>(groupViewState.editGroupState)
    }

    /**
     * Sets up the ViewModel with a pre-loaded list of groups for testing.
     *
     * This helper function initializes the [MainViewModel] and simulates a successful fetch
     * of all groups, which is a common precondition for many tests. It internally mocks
     * [GetAllGroupsUseCase] to return a fixed list of groups.
     *
     * It allows injecting mock use cases to tailor the ViewModel's behavior for specific test
     * scenarios.
     *
     * @param testScope The coroutine test scope.
     * @param getAllLocationsForGroupUseCase Mock implementation of [GetAllLocationsForGroupUseCase].
     * @param addGroupUseCase Mock implementation of [AddGroupUseCase].
     * @param deleteGroupsUseCase Mock implementation of [DeleteGroupsUseCase].
     * @param editGroupUseCase Mock implementation of [EditGroupUseCase].
     * @return An instance of [MainViewModel] with its state reflecting the loaded groups.
     */
    private fun fetchAllGroups(
        testScope: TestScope,
        getAllLocationsForGroupUseCase: GetAllLocationsForGroupUseCase = mock(),
        addGroupUseCase: AddGroupUseCase = mock(),
        deleteGroupsUseCase: DeleteGroupsUseCase = mock(),
        editGroupUseCase: EditGroupUseCase = mock()
    ): MainViewModel {
        val groupsList = listOf(
            Group(1, "Group 1"),
            Group(2, "Group 2"),
        )
        val getAllGroupsUseCase = mock<GetAllGroupsUseCase> {
            everySuspend { execute() } returns flowOf(groupsList)
        }
        val mainViewModel = MainViewModel(
            getAllGroupsUseCase = getAllGroupsUseCase,
            getAllLocationsForGroupUseCase = getAllLocationsForGroupUseCase,
            addGroupUseCase = addGroupUseCase,
            deleteGroupsUseCase = deleteGroupsUseCase,
            editGroupUseCase = editGroupUseCase
        )
        testScope.runCurrent()
        return mainViewModel
    }

    /**
     * Sets up the ViewModel with pre-loaded locations for a specific group.
     *
     * This helper function initializes the [MainViewModel] and simulates a successful fetch of
     * both groups and locations for a specific group. This is a common precondition for tests
     * operating on a list of locations.
     *
     * It internally mocks [GetAllGroupsUseCase] and [GetAllLocationsForGroupUseCase] to return
     * a fixed list of groups and locations, respectively.
     *
     * It allows injecting mock use cases to tailor the ViewModel's behavior for specific test
     * scenarios.
     *
     * @param testScope The coroutine test scope.
     * @param addGroupUseCase Mock implementation of [AddGroupUseCase].
     * @param deleteGroupsUseCase Mock implementation of [DeleteGroupsUseCase].
     * @param editGroupUseCase Mock implementation of [EditGroupUseCase].
     * @return An instance of [MainViewModel] with its state reflecting the loaded groups and
     * locations.
     */
    private fun fetchLocationsForGroup(
        testScope: TestScope,
        addGroupUseCase: AddGroupUseCase = mock(),
        deleteGroupsUseCase: DeleteGroupsUseCase = mock(),
        editGroupUseCase: EditGroupUseCase = mock()
    ): MainViewModel {
        val groupsList = listOf(
            Group(1, "Group 1"),
            Group(2, "Group 2"),
        )
        val getAllGroupsUseCase = mock<GetAllGroupsUseCase> {
            everySuspend { execute() } returns flowOf(groupsList)
        }
        val expectedLocationList = mutableListOf(
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
        val groupModel = GroupModel(
            id = 1,
            name = "Group 1"
        )
        val getAllLocationsForGroupUseCase = mock<GetAllLocationsForGroupUseCase> {
            everySuspend { execute(groupModel.id) } returns flowOf(expectedLocationList)
        }
        val mainViewModel = MainViewModel(
            getAllGroupsUseCase = getAllGroupsUseCase,
            getAllLocationsForGroupUseCase = getAllLocationsForGroupUseCase,
            addGroupUseCase = addGroupUseCase,
            deleteGroupsUseCase = deleteGroupsUseCase,
            editGroupUseCase = editGroupUseCase
        )
        testScope.runCurrent()
        mainViewModel.onEvent(ViewEvent.FetchLocationsForGroup(groupModel))
        testScope.runCurrent()
        return mainViewModel
    }
}
