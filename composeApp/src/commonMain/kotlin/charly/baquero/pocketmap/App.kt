package charly.baquero.pocketmap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import charly.baquero.pocketmap.di.appModule
import charly.baquero.pocketmap.ui.MainScreen
import charly.baquero.pocketmap.ui.MainViewModel
import charly.baquero.pocketmap.ui.ViewEvent
import charly.baquero.pocketmap.ui.navigation.Screen
import com.charly.core.di.coreModule
import com.charly.domain.di.domainModule
import com.charly.startup.di.startUpModule
import com.charly.startup.ui.StartUpScreen
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.KoinAppDeclaration

@Composable
@Preview
fun App(koinAppDeclaration: KoinAppDeclaration? = null) {
    KoinApplication(application = {
        koinAppDeclaration?.invoke(this)
        modules(appModule, domainModule, coreModule, startUpModule)
    }) {
        MaterialTheme {
            Scaffold {
                MainNavigationHost()
            }
        }
    }
}

@Composable
private fun MainNavigationHost() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Screen.StartUp,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Screen.StartUp> {
            StartUpScreen(onStartUpSuccess = {
                navController.navigate(Screen.Main) {
                    launchSingleTop = true
                    popUpTo(Screen.StartUp) {
                        inclusive = true
                    }
                }
            })
        }
        composable<Screen.Main> {
            val mainViewModel = koinViewModel<MainViewModel>()
            val mainViewState by mainViewModel.state.collectAsStateWithLifecycle()
            val groupViewState = mainViewState.groupViewState
            val locationsViewState = mainViewState.locationsViewState
            val dialogState = mainViewState.dialogState
            MainScreen(
                groupViewState = groupViewState,
                locationsViewState = locationsViewState,
                dialogState = dialogState,
                onGroupClick = { group ->
                    mainViewModel.onEvent(ViewEvent.FetchLocationsForGroup(group))
                },
                onGroupLongClick = { group ->
                    mainViewModel.onEvent(ViewEvent.DisplayGroupOptionsMenu(group))
                },
                onLocationClick = { location ->
                    mainViewModel.onEvent(ViewEvent.LocationClick(location))
                },
                onClearMapClick = { mainViewModel.onEvent(ViewEvent.ClearMap) },
                fetchAllGroups = { mainViewModel.onEvent(ViewEvent.FetchAllGroups) },
                onCreateGroupClick = { mainViewModel.onEvent(ViewEvent.ShowCreateGroupDialog) },
                onDeleteGroupsClick = { mainViewModel.onEvent(ViewEvent.ShowDeleteGroupsDialog) },
                onEditGroupClick = { mainViewModel.onEvent(ViewEvent.ShowEditGroupDialog) },
                onGroupOptionsMenuBackClick = { mainViewModel.onEvent(ViewEvent.DismissGroupOptionsMenu) },
                createGroup = { groupName ->
                    mainViewModel.onEvent(ViewEvent.CreateGroup(groupName))
                },
                onDismissDialog = { mainViewModel.onEvent(ViewEvent.DismissDialog) },
                deleteGroups = { mainViewModel.onEvent(ViewEvent.DeleteGroups) },
                editGroup = { groupName ->
                    mainViewModel.onEvent(ViewEvent.EditGroup(groupName))
                },
                onMarkerClick = { locationModel ->
                    mainViewModel.onEvent(ViewEvent.OnMarkerClick(locationModel))
                }
            )
        }
    }
}
