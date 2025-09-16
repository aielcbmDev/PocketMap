package charly.baquero.pocketmap.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import charly.baquero.pocketmap.ui.navigation.BottomTab
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapTopBar() {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {},
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Option 1") },
                    onClick = { /* Do something... */ }
                )
                DropdownMenuItem(
                    text = { Text("Option 2") },
                    onClick = { /* Do something... */ }
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MapAppContent() {
    Scaffold(
        topBar = { MapTopBar() }
    ) { _ ->
        MapPane()
    }
}
