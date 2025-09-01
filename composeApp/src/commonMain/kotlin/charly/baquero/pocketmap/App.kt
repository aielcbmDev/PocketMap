package charly.baquero.pocketmap

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.di.appModule
import charly.baquero.pocketmap.di.platformModule
import charly.baquero.pocketmap.ui.map.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
@Preview
fun App(koinAppDeclaration: KoinAppDeclaration? = null) {
    KoinApplication(application = {
        koinAppDeclaration?.invoke(this)
        modules(appModule, platformModule)
    }) {
        MaterialTheme {
            Scaffold {
                MainScreen()
            }
        }
    }
}
