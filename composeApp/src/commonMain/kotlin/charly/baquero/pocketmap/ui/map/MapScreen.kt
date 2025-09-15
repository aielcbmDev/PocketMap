package charly.baquero.pocketmap.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import charly.baquero.pocketmap.ui.navigation.BottomTab
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun MapScreen(
    onTabSelected: (BottomTab) -> Unit,
    selectedTab: BottomTab
) {
    Box(modifier = Modifier.fillMaxSize()) {
        MapNavigationWrapperUI(
            onTabSelected = onTabSelected,
            selectedTab = selectedTab
        ) {
            MapAppContent()
        }
    }
}

@Composable
fun MapNavigationWrapperUI(
    onTabSelected: (BottomTab) -> Unit,
    selectedTab: BottomTab,
    content: @Composable () -> Unit
) {
    val windowSize = with(LocalDensity.current) {
        val windowInfo = LocalWindowInfo.current
        windowInfo.containerSize.toSize().toDpSize()
    }
    val layoutType = if (windowSize.width >= 1200.dp) {
        NavigationSuiteType.NavigationDrawer
    } else {
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
            currentWindowAdaptiveInfo()
        )
    }

    NavigationSuiteScaffold(
        layoutType = layoutType,
        navigationSuiteItems = {
            BottomTab.entries.forEach {
                item(
                    selected = it == selectedTab,
                    onClick = {
                        onTabSelected(it)
                    },
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = stringResource(it.labelRes)
                        )
                    },
                    label = {
                        Text(text = stringResource(it.labelRes))
                    },
                )
            }
        }
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MapAppContent() {
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
    val coroutineScope = rememberCoroutineScope()
    BackHandler(navigator.canNavigateBack()) {
        coroutineScope.launch {
            navigator.navigateBack()
        }
    }

    MapPane()
}
