package charly.baquero.pocketmap.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Scaffold { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
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
    }
}
