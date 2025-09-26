package charly.baquero.pocketmap.ui.display

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import charly.baquero.pocketmap.domain.model.Group
import charly.baquero.pocketmap.domain.model.Location
import charly.baquero.pocketmap.ui.DisplayGroupViewState
import com.charly.startup.ui.ErrorContent
import com.charly.startup.ui.LoadingContent
import kotlinx.coroutines.launch

@Composable
fun DisplayDataScreen(
    displayGroupState: DisplayGroupViewState,
    onGroupClick: (Group) -> Unit,
    onLocationClick: (Location) -> Unit,
    fetchAllGroups: () -> Unit
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
                    onLocationClick = onLocationClick
                )
            }

            is DisplayGroupViewState.Error -> {
                ErrorContent(
                    { fetchAllGroups.invoke() }
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
    onLocationClick: (Location) -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
    val coroutineScope = rememberCoroutineScope()
    val canNavigateBack = navigator.canNavigateBack()
    BackHandler(canNavigateBack) {
        coroutineScope.launch {
            navigator.navigateBack()
        }
    }
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
