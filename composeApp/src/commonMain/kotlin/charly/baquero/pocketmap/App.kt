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
import charly.baquero.pocketmap.ui.navigation.Screen
import com.charly.database.di.databaseModule
import com.charly.domain.di.domainModule
import com.charly.startup.di.startUpModule
import com.charly.startup.ui.StartUpScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.KoinAppDeclaration

@Composable
@Preview
fun App(koinAppDeclaration: KoinAppDeclaration? = null) {
    KoinApplication(application = {
        koinAppDeclaration?.invoke(this)
        modules(appModule, domainModule, databaseModule, startUpModule)
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
            val viewEvent = mainViewState.viewEvent
            MainScreen(
                groupViewState = groupViewState,
                locationsViewState = locationsViewState,
                viewEvent = viewEvent,
                onGroupClick = mainViewModel::fetchLocationsForGroup,
                onGroupLongClick = mainViewModel::displayGroupOptionsMenu,
                onLocationClick = mainViewModel::onLocationClick,
                onClearMapClick = mainViewModel::onClearMapClick,
                fetchAllGroups = mainViewModel::fetchAllGroups,
                onCreateGroupClick = mainViewModel::showCreateGroupDialog,
                onGroupOptionsMenuBackClick = mainViewModel::dismissGroupOptionsMenu,
                createGroup = mainViewModel::createGroup,
                onDismissCreateGroupDialog = mainViewModel::dismissCreateGroupDialog
            )
        }
    }
}
