package charly.baquero.pocketmap.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import charly.baquero.pocketmap.ui.ViewEvent
import charly.baquero.pocketmap.ui.DisplayGroupViewState
import charly.baquero.pocketmap.ui.common.CreateGroupDialog
import charly.baquero.pocketmap.ui.common.IconButtonWithRichTooltip
import org.jetbrains.compose.resources.stringResource
import pocketmap.composeapp.generated.resources.Res
import pocketmap.composeapp.generated.resources.map_screen_add_group_tooltip_description
import pocketmap.composeapp.generated.resources.map_screen_add_group_tooltip_title
import pocketmap.composeapp.generated.resources.map_screen_clear_map_option
import pocketmap.composeapp.generated.resources.more_options

@Composable
fun MapScreen(
    displayGroupState: DisplayGroupViewState,
    viewEvent: ViewEvent?,
    onClearMapClick: () -> Unit,
    onCreateGroupClick: () -> Unit,
    createGroup: (String) -> Unit,
    onDismissCreateGroupDialog: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                MapTopBar(
                    onClearMapClick = onClearMapClick,
                    onCreateGroupClick = onCreateGroupClick
                )
            }
        ) { _ ->
            MapPane(displayGroupState)
            DisplayViewEvent(
                viewEvent = viewEvent,
                createGroup = createGroup,
                onDismissCreateGroupDialog = onDismissCreateGroupDialog
            )
        }
    }
}

@Composable
fun DisplayViewEvent(
    viewEvent: ViewEvent?,
    createGroup: (String) -> Unit,
    onDismissCreateGroupDialog: () -> Unit
) {
    viewEvent?.let { it ->
        when (it) {
            is ViewEvent.CreateGroupDialog -> CreateGroupDialog(
                createGroup = { groupName ->
                    createGroup.invoke(groupName)
                },
                dismissDialog = {
                    onDismissCreateGroupDialog.invoke()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapTopBar(
    onClearMapClick: () -> Unit,
    onCreateGroupClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {},
        actions = {
            IconButtonWithRichTooltip(
                tooltipTitle = stringResource(Res.string.map_screen_add_group_tooltip_title),
                tooltipText = stringResource(Res.string.map_screen_add_group_tooltip_description),
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(Res.string.map_screen_add_group_tooltip_title),
                onClick = { onCreateGroupClick.invoke() }
            )
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = stringResource(Res.string.more_options)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(Res.string.map_screen_clear_map_option)) },
                    onClick = {
                        onClearMapClick.invoke()
                        expanded = false
                    }
                )
            }
        },
    )
}
