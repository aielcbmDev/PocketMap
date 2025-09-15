package charly.baquero.pocketmap.ui.display

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import charly.baquero.pocketmap.ui.navigation.BottomTab
import com.charly.database.model.groups.Group
import com.charly.startup.ui.ErrorContent
import com.charly.startup.ui.LoadingContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun DisplayDataScreen(
    displayGroupState: DisplayGroupViewState,
    onGroupClick: (Group) -> Unit,
    onTabSelected: (BottomTab) -> Unit,
    selectedTab: BottomTab
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (val currentState = displayGroupState) {
            is DisplayGroupViewState.Loading -> {
                LoadingContent()
            }

            is DisplayGroupViewState.Success -> {
                GroupsNavigationWrapperUI(
                    onTabSelected = onTabSelected,
                    selectedTab = selectedTab,
                    content = {
                        GroupsAppContent(
                            displayGroupState = currentState,
                            onGroupClick = onGroupClick
                        )
                    }
                )
            }

            is DisplayGroupViewState.Error -> {
                ErrorContent(
                    { }
                )
            }

            DisplayGroupViewState.Empty -> {}
        }
    }
}

@Composable
private fun GroupsNavigationWrapperUI(
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
fun GroupsAppContent(
    displayGroupState: DisplayGroupViewState.Success,
    onGroupClick: (Group) -> Unit,
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    BackHandler(navigator.canNavigateBack()) {
        applicationScope.launch {
            navigator.navigateBack()
        }
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            GroupListPane(
                displayGroupState = displayGroupState,
                onGroupClick = { group ->
                    onGroupClick(group)
                    applicationScope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, group.id)
                    }
                }
            )
        },
        detailPane = {
            GroupDetailPane(displayGroupState.displayLocationsViewState)
        }
    )
}
