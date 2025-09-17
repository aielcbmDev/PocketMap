package charly.baquero.pocketmap.ui.display

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import charly.baquero.pocketmap.domain.model.Group
import com.charly.startup.ui.ErrorContent
import com.charly.startup.ui.LoadingContent
import kotlinx.coroutines.launch

@Composable
fun DisplayDataScreen(
    displayGroupState: DisplayGroupViewState,
    onGroupClick: (Group) -> Unit,
    layoutType: NavigationSuiteType
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (val currentState = displayGroupState) {
            is DisplayGroupViewState.Loading -> {
                LoadingContent()
            }

            is DisplayGroupViewState.Success -> {
                GroupsAppContent(
                    displayGroupState = currentState,
                    onGroupClick = onGroupClick,
                    layoutType = layoutType
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

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun GroupsAppContent(
    displayGroupState: DisplayGroupViewState.Success,
    onGroupClick: (Group) -> Unit,
    layoutType: NavigationSuiteType
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
