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
import charly.baquero.pocketmap.ui.DialogState
import charly.baquero.pocketmap.ui.GroupViewState
import charly.baquero.pocketmap.ui.LocationsViewState
import charly.baquero.pocketmap.ui.model.GroupModel
import charly.baquero.pocketmap.ui.model.LocationModel
import com.charly.startup.ui.ErrorContent
import com.charly.startup.ui.LoadingContent
import kotlinx.coroutines.launch

@Composable
fun DisplayDataScreen(
    groupViewState: GroupViewState,
    locationsViewState: LocationsViewState,
    onGroupClick: (GroupModel) -> Unit,
    onGroupLongClick: (GroupModel) -> Unit,
    onLocationClick: (LocationModel) -> Unit,
    fetchAllGroups: () -> Unit,
    onCreateGroupClick: () -> Unit,
    onDeleteGroupsClick: () -> Unit,
    onEditGroupClick: () -> Unit,
    onGroupOptionsMenuBackClick: () -> Unit,
    dialogState: DialogState,
    createGroup: (String) -> Unit,
    onDismissDialog: () -> Unit,
    deleteGroups: () -> Unit,
    editGroup: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (val currentState = groupViewState) {
            is GroupViewState.Loading -> {
                LoadingContent()
            }

            is GroupViewState.Success -> {
                GroupsAppContent(
                    groupViewState = currentState,
                    locationsViewState = locationsViewState,
                    onGroupClick = onGroupClick,
                    onGroupLongClick = onGroupLongClick,
                    onLocationClick = onLocationClick,
                    onCreateGroupClick = onCreateGroupClick,
                    onDeleteGroupsClick = onDeleteGroupsClick,
                    onEditGroupClick = onEditGroupClick,
                    onOptionsMenuBackClick = onGroupOptionsMenuBackClick,
                    dialogState = dialogState,
                    createGroup = createGroup,
                    onDismissDialog = onDismissDialog,
                    deleteGroups = deleteGroups,
                    editGroup = editGroup
                )
            }

            is GroupViewState.Error -> {
                ErrorContent(
                    { fetchAllGroups.invoke() }
                )
            }

            GroupViewState.Empty -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun GroupsAppContent(
    groupViewState: GroupViewState.Success,
    locationsViewState: LocationsViewState,
    onGroupClick: (GroupModel) -> Unit,
    onGroupLongClick: (GroupModel) -> Unit,
    onLocationClick: (LocationModel) -> Unit,
    onCreateGroupClick: () -> Unit,
    onDeleteGroupsClick: () -> Unit,
    onEditGroupClick: () -> Unit,
    onOptionsMenuBackClick: () -> Unit,
    dialogState: DialogState,
    createGroup: (String) -> Unit,
    onDismissDialog: () -> Unit,
    deleteGroups: () -> Unit,
    editGroup: (String) -> Unit
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
                    groupViewState = groupViewState,
                    onGroupClick = { group ->
                        onGroupClick(group)
                        coroutineScope.launch {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, group.id)
                        }
                    },
                    onGroupLongClick = onGroupLongClick,
                    onCreateGroupClick = onCreateGroupClick,
                    onDeleteGroupsClick = onDeleteGroupsClick,
                    onEditGroupClick = onEditGroupClick,
                    onGroupOptionsMenuBackClick = onOptionsMenuBackClick,
                    dialogState = dialogState,
                    createGroup = createGroup,
                    onDismissDialog = onDismissDialog,
                    deleteGroups = deleteGroups,
                    editGroup = editGroup
                )
            }
        },
        detailPane = {
            AnimatedPane {
                LocationListPane(
                    locationsViewState = locationsViewState,
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
