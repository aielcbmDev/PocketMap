package charly.baquero.pocketmap

import androidx.compose.ui.window.ComposeUIViewController
import charly.baquero.pocketmap.ui.model.LocationModel
import platform.UIKit.UIViewController

fun MainViewController(
    mapUIViewController: (List<LocationModel>?, LocationModel?) -> UIViewController
) = ComposeUIViewController {
    mapViewController = mapUIViewController
    App()
}

lateinit var mapViewController: (List<LocationModel>?, LocationModel?) -> UIViewController
