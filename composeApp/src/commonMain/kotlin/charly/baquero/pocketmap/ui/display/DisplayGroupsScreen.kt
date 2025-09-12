package charly.baquero.pocketmap.ui.display

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import charly.baquero.pocketmap.ui.navigation.BottomTabDestination
import charly.baquero.pocketmap.ui.startup.ErrorContent
import charly.baquero.pocketmap.ui.startup.LoadingContent
import com.charly.database.model.groups.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DisplayGroupsScreen() {
    val viewModel = koinViewModel<DisplayGroupsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when (val currentState = state) {
                is DisplayGroupsViewState.Loading -> {
                    LoadingContent()
                }

                is DisplayGroupsViewState.Success -> {
                    GroupsNavigationWrapperUI {
                        GroupsAppContent(
                            displayGroupUIState = currentState,
                            onGroupClick = viewModel::setSelectedGroup
                        )
                    }
                }

                is DisplayGroupsViewState.Error -> {
                    ErrorContent(
                        { }
                    )
                }

                DisplayGroupsViewState.Empty -> {}
            }
        }
    }
}

@Composable
private fun GroupsNavigationWrapperUI(
    content: @Composable () -> Unit = {}
) {
    var selectedTab: BottomTabDestination by remember {
        mutableStateOf(BottomTabDestination.Groups)
    }
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
            BottomTabDestination.entries.forEach {
                item(
                    selected = it == selectedTab,
                    onClick = { /*TODO update selection*/ },
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
    displayGroupUIState: DisplayGroupsViewState.Success,
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
                displayGroupUIState = displayGroupUIState,
                onGroupClick = { group ->
                    onGroupClick(group)
                    applicationScope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, group.id)
                    }
                }
            )
        },
        detailPane = {
            GroupDetailPane(displayGroupUIState.displayLocationsViewState)
        }
    )
}
