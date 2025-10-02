package charly.baquero.pocketmap.ui.display

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import charly.baquero.pocketmap.ui.GroupViewState
import charly.baquero.pocketmap.ui.ViewEvent
import charly.baquero.pocketmap.ui.common.DisplayViewEvent
import charly.baquero.pocketmap.ui.common.IconButtonWithRichTooltip
import charly.baquero.pocketmap.ui.model.GroupModel
import org.jetbrains.compose.resources.stringResource
import pocketmap.composeapp.generated.resources.Res
import pocketmap.composeapp.generated.resources.groups_screen_add_group_tooltip_text
import pocketmap.composeapp.generated.resources.groups_screen_add_group_tooltip_title
import pocketmap.composeapp.generated.resources.groups_screen_delete_group_tooltip_text
import pocketmap.composeapp.generated.resources.groups_screen_delete_group_tooltip_title
import pocketmap.composeapp.generated.resources.groups_screen_edit_group_tooltip_text
import pocketmap.composeapp.generated.resources.groups_screen_edit_group_tooltip_title
import pocketmap.composeapp.generated.resources.groups_screen_title

@Composable
fun GroupListPane(
    groupViewState: GroupViewState.Success,
    onGroupClick: (GroupModel) -> Unit,
    onGroupLongClick: (GroupModel) -> Unit,
    onCreateGroupClick: () -> Unit,
    onGroupOptionsMenuBackClick: () -> Unit,
    viewEvent: ViewEvent?,
    createGroup: (String) -> Unit,
    onDismissCreateGroupDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            GroupListPaneTopBar(
                groupViewState = groupViewState,
                onCreateGroupClick = onCreateGroupClick,
                onGroupOptionsMenuBackClick = onGroupOptionsMenuBackClick
            )
        }
    ) { padding ->
        val state = rememberLazyListState()
        LazyColumn(
            state = state,
            modifier = modifier.padding(padding)
        ) {
            items(
                items = groupViewState.groupList,
                key = { item -> item.id }
            ) { group ->
                GroupListItem(
                    group = group,
                    onGroupClick = onGroupClick,
                    onGroupLongClick = onGroupLongClick
                )
            }
        }
        DisplayViewEvent(
            viewEvent = viewEvent,
            createGroup = createGroup,
            onDismissCreateGroupDialog = onDismissCreateGroupDialog
        )
    }
}

@Composable
fun GroupListItem(
    group: GroupModel,
    onGroupClick: (GroupModel) -> Unit,
    onGroupLongClick: (GroupModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onGroupClick(group) },
                onLongClick = { onGroupLongClick(group) }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = group.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GroupListPaneTopBar(
    groupViewState: GroupViewState.Success,
    onCreateGroupClick: () -> Unit,
    onGroupOptionsMenuBackClick: () -> Unit
) {
    if (groupViewState.displayOptionsMenu) {
        TopAppBar(
            title = { Text(stringResource(Res.string.groups_screen_title)) },
            navigationIcon = {
                IconButton(onClick = { onGroupOptionsMenuBackClick.invoke() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButtonWithRichTooltip(
                    tooltipTitle = stringResource(Res.string.groups_screen_edit_group_tooltip_title),
                    tooltipText = stringResource(Res.string.groups_screen_edit_group_tooltip_text),
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(Res.string.groups_screen_edit_group_tooltip_title),
                    onClick = { }
                )
                IconButtonWithRichTooltip(
                    tooltipTitle = stringResource(Res.string.groups_screen_delete_group_tooltip_title),
                    tooltipText = stringResource(Res.string.groups_screen_delete_group_tooltip_text),
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = stringResource(Res.string.groups_screen_delete_group_tooltip_title),
                    onClick = { }
                )
            },
        )
    } else {
        TopAppBar(
            title = { Text(stringResource(Res.string.groups_screen_title)) },
            actions = {
                IconButtonWithRichTooltip(
                    tooltipTitle = stringResource(Res.string.groups_screen_add_group_tooltip_title),
                    tooltipText = stringResource(Res.string.groups_screen_add_group_tooltip_text),
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(Res.string.groups_screen_add_group_tooltip_title),
                    onClick = { onCreateGroupClick.invoke() }
                )
            }
        )
    }
}
