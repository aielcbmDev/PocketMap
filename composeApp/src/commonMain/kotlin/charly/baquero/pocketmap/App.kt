package charly.baquero.pocketmap

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.ui.map.MapComponent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold {
            MapComponent()
        }
    }
}
