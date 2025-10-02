package charly.baquero.pocketmap.ui

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import charly.baquero.pocketmap.ui.common.IconButtonWithRichTooltip
import charly.baquero.pocketmap.ui.display.DisplayDataScreen
import charly.baquero.pocketmap.ui.map.MapScreen
import charly.baquero.pocketmap.ui.model.GroupModel
import charly.baquero.pocketmap.ui.model.LocationModel
import charly.baquero.pocketmap.ui.navigation.BottomTab
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScreen(
    groupViewState: GroupViewState,
    locationsViewState: LocationsViewState,
    viewState: ViewState?,
    onGroupClick: (GroupModel) -> Unit,
    onGroupLongClick: (GroupModel) -> Unit,
    onLocationClick: (LocationModel) -> Unit,
    onClearMapClick: () -> Unit,
    fetchAllGroups: () -> Unit,
    onCreateGroupClick: () -> Unit,
    onGroupOptionsMenuBackClick: () -> Unit,
    createGroup: (String) -> Unit,
    onDismissCreateGroupDialog: () -> Unit
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
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    NavigationSuiteScaffold(
        layoutType = layoutType,
        navigationSuiteItems = {
            BottomTab.entries.forEach {
                item(
                    selected = currentRoute == it.route,
                    onClick = {},
                    icon = {
                        IconButtonWithRichTooltip(
                            groupViewState = groupViewState,
                            bottomTab = it,
                            navController = navController
                        )
                    },
                    enabled = it.route == BottomTab.Map.route || groupViewState != GroupViewState.Empty,
                    label = {
                        Text(text = stringResource(it.labelRes))
                    },
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = BottomTab.Map.route,
            modifier = Modifier
        ) {
            composable(BottomTab.Map.route) {
                MapScreen(
                    locationsViewState = locationsViewState,
                    viewState = viewState,
                    onClearMapClick = onClearMapClick,
                    onCreateGroupClick = onCreateGroupClick,
                    createGroup = createGroup,
                    onDismissCreateGroupDialog = onDismissCreateGroupDialog
                )
            }
            composable(BottomTab.Groups.route) {
                DisplayDataScreen(
                    groupViewState = groupViewState,
                    locationsViewState = locationsViewState,
                    onGroupClick = onGroupClick,
                    onGroupLongClick = onGroupLongClick,
                    onLocationClick = { location ->
                        onLocationClick.invoke(location)
                        navController.popBackStack(
                            route = BottomTab.Map.route,
                            inclusive = false
                        )
                    },
                    fetchAllGroups = fetchAllGroups,
                    onCreateGroupClick = onCreateGroupClick,
                    onGroupOptionsMenuBackClick = onGroupOptionsMenuBackClick,
                    viewState = viewState,
                    createGroup = createGroup,
                    onDismissCreateGroupDialog = onDismissCreateGroupDialog
                )
            }
        }
    }
}

@Composable
private fun IconButtonWithRichTooltip(
    groupViewState: GroupViewState,
    bottomTab: BottomTab,
    navController: NavHostController,
) {
    IconButtonWithRichTooltip(
        tooltipTitle = stringResource(bottomTab.tooltipTitle),
        tooltipText = stringResource(bottomTab.tooltipText),
        imageVector = bottomTab.icon,
        contentDescription = stringResource(bottomTab.contentDescription),
        onClick = {
            when (bottomTab.route) {
                BottomTab.Map.route -> {
                    navController.popBackStack(
                        route = BottomTab.Map.route,
                        inclusive = false
                    )
                }

                BottomTab.Groups.route -> {
                    if (groupViewState == GroupViewState.Empty) {
                        return@IconButtonWithRichTooltip
                    }
                    navController.navigate(BottomTab.Groups.route) {
                        launchSingleTop = true
                    }
                }
            }
        }
    )
}
