package charly.baquero.pocketmap.ui

import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.ui.display.DisplayDataScreen
import charly.baquero.pocketmap.ui.display.DisplayGroupViewState
import charly.baquero.pocketmap.ui.map.MapScreen
import charly.baquero.pocketmap.ui.navigation.BottomTab
import com.charly.database.model.groups.Group

@Composable
fun MainScreen(
    mainViewState: MainViewState,
    displayGroupState: DisplayGroupViewState,
    onGroupClick: (Group) -> Unit,
    onTabSelected: (BottomTab) -> Unit
) {
    when (mainViewState) {
        is MainViewState.MapTabSelected -> {
            MapScreen(
                onTabSelected = onTabSelected,
                selectedTab = BottomTab.Map
            )
        }

        is MainViewState.GroupsTabSelected -> {
            DisplayDataScreen(
                displayGroupState = displayGroupState,
                onGroupClick = onGroupClick,
                onTabSelected = onTabSelected,
                selectedTab = BottomTab.Groups
            )
        }
    }
}
