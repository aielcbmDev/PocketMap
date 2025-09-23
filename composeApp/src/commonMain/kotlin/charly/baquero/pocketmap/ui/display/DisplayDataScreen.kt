package charly.baquero.pocketmap.ui.display

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import charly.baquero.pocketmap.domain.model.Group
import charly.baquero.pocketmap.ui.DisplayGroupViewState
import com.charly.startup.ui.ErrorContent
import com.charly.startup.ui.LoadingContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DisplayDataScreen(
    displayGroupState: DisplayGroupViewState,
    onGroupClick: (Group) -> Unit,
    onLocationClick: () -> Unit,
    navigator: ThreePaneScaffoldNavigator<Long>,
    coroutineScope: CoroutineScope,
    canNavigateBack: Boolean
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
                    onLocationClick = onLocationClick,
                    navigator = navigator,
                    coroutineScope = coroutineScope,
                    canNavigateBack = canNavigateBack
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

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun GroupsAppContent(
    displayGroupState: DisplayGroupViewState.Success,
    onGroupClick: (Group) -> Unit,
    onLocationClick: () -> Unit,
    navigator: ThreePaneScaffoldNavigator<Long>,
    coroutineScope: CoroutineScope,
    canNavigateBack: Boolean
) {
    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                GroupListPane(
                    displayGroupState = displayGroupState,
                    onGroupClick = { group ->
                        onGroupClick(group)
                        coroutineScope.launch {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, group.id)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                LocationListPane(
                    displayLocationsViewState = displayGroupState.displayLocationsViewState,
                    onBackClick = {
                        coroutineScope.launch {
                            navigator.navigateBack()
                        }
                    },
                    onLocationClick = onLocationClick,
                    canNavigateBack = canNavigateBack
                )
            }
        }
    )
}
