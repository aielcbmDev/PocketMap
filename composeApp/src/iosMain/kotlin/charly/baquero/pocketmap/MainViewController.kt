package charly.baquero.pocketmap

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MainViewController(
    mapUIViewController: (String) -> UIViewController
) = ComposeUIViewController {
    mapViewController = mapUIViewController
    App()
}

lateinit var mapViewController: (String) -> UIViewController

