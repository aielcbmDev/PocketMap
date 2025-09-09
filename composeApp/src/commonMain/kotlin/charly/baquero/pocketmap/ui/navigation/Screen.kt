package charly.baquero.pocketmap.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object StartUp : Screen

    @Serializable
    data object Map : Screen

    @Serializable
    data object DisplayDataTabs : Screen
}
