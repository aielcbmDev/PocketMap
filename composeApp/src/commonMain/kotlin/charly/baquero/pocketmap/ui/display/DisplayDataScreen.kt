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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import charly.baquero.pocketmap.ui.navigation.BottomTab
import charly.baquero.pocketmap.domain.model.Group
import com.charly.startup.ui.ErrorContent
import com.charly.startup.ui.LoadingContent
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
                GroupsNavigationWrapperUI(
                    onTabSelected = onTabSelected,
                    selectedTab = selectedTab,
                    layoutType = layoutType,
                    content = {
                        GroupsAppContent(
                            displayGroupState = currentState,
                            onGroupClick = onGroupClick,
                            layoutType = layoutType
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
    layoutType: NavigationSuiteType,
    content: @Composable () -> Unit
) {
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
    layoutType: NavigationSuiteType,
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
    val coroutineScope = rememberCoroutineScope()
    BackHandler(navigator.canNavigateBack()) {
        coroutineScope.launch {
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
                    coroutineScope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, group.id)
                    }
                }
            )
        },
        detailPane = {
            LocationListPane(
                displayLocationsViewState = displayGroupState.displayLocationsViewState,
                onBackClick = {
                    coroutineScope.launch {
                        navigator.navigateBack()
                    }
                },
                layoutType = layoutType
            )
        }
    )
}
