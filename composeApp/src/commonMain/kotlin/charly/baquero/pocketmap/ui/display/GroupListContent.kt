package charly.baquero.pocketmap.ui.display

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import charly.baquero.pocketmap.domain.model.Group
import charly.baquero.pocketmap.ui.DisplayGroupViewState
import charly.baquero.pocketmap.ui.common.IconButtonWithRichTooltip
import org.jetbrains.compose.resources.stringResource
import pocketmap.composeapp.generated.resources.Res
import pocketmap.composeapp.generated.resources.groups_screen_add_group_tooltip_text
import pocketmap.composeapp.generated.resources.groups_screen_add_group_tooltip_title
import pocketmap.composeapp.generated.resources.groups_screen_delete_group_option
import pocketmap.composeapp.generated.resources.groups_screen_edit_group_option
import pocketmap.composeapp.generated.resources.groups_screen_title
import pocketmap.composeapp.generated.resources.more_options

@Composable
fun GroupListPane(
    displayGroupState: DisplayGroupViewState.Success,
    onGroupClick: (Group) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { GroupListPaneTopBar() }
    ) { padding ->
        val state = rememberLazyListState()
        LazyColumn(
            state = state,
            modifier = modifier.padding(padding)
        ) {
            items(displayGroupState.groupList) { group ->
                GroupListItem(
                    group = group,
                    onGroupClick = onGroupClick
                )
            }
        }
    }
}

@Composable
fun GroupListItem(
    group: Group,
    onGroupClick: (Group) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onGroupClick(group) },
        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp)
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
private fun GroupListPaneTopBar() {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(stringResource(Res.string.groups_screen_title)) },
        actions = {
            IconButtonWithRichTooltip(
                tooltipTitle = stringResource(Res.string.groups_screen_add_group_tooltip_title),
                tooltipText = stringResource(Res.string.groups_screen_add_group_tooltip_text),
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(Res.string.groups_screen_add_group_tooltip_title),
                onClick = {}
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
                    text = { Text(stringResource(Res.string.groups_screen_edit_group_option)) },
                    onClick = { /* Do something... */ }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(Res.string.groups_screen_delete_group_option)) },
                    onClick = { /* Do something... */ }
                )
            }
        },
    )
}
