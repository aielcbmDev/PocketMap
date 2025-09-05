package charly.baquero.pocketmap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import charly.baquero.pocketmap.di.appDatabaseModule
import charly.baquero.pocketmap.di.appModule
import charly.baquero.pocketmap.di.platformModule
import charly.baquero.pocketmap.ui.map.MapScreen
import charly.baquero.pocketmap.ui.navigation.Screen
import charly.baquero.pocketmap.ui.startup.StartUpScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
@Preview
fun App(koinAppDeclaration: KoinAppDeclaration? = null) {
    KoinApplication(application = {
        koinAppDeclaration?.invoke(this)
        modules(appModule, platformModule, appDatabaseModule)
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
                navController.navigate(Screen.Map) {
                    popUpTo(Screen.StartUp) {
                        inclusive = true
                    }
                }
            })
        }
        composable<Screen.Map> {
            MapScreen()
        }
    }
}
