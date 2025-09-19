package charly.baquero.pocketmap.ui.display

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import charly.baquero.pocketmap.domain.model.Location
import charly.baquero.pocketmap.ui.DisplayLocationsViewState
import org.jetbrains.compose.resources.stringResource
import pocketmap.composeapp.generated.resources.Res
import pocketmap.composeapp.generated.resources.groups_screen_delete_location_option
import pocketmap.composeapp.generated.resources.groups_screen_edit_location_option

@Composable
fun LocationListPane(
    displayLocationsViewState: DisplayLocationsViewState,
    onBackClick: () -> Unit,
    onLocationClick: () -> Unit,
    layoutType: NavigationSuiteType,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            LocationListPaneTopBar(
                groupName = (displayLocationsViewState as? DisplayLocationsViewState.Success)?.groupName
                    ?: "",
                onBackClick = onBackClick,
                layoutType = layoutType
            )
        }
    ) { padding ->
        when (val currentState = displayLocationsViewState) {
            is DisplayLocationsViewState.Loading -> {}

            is DisplayLocationsViewState.Success -> {
                val state = rememberLazyListState()
                LazyColumn(
                    state = state,
                    modifier = modifier.fillMaxWidth().padding(padding)
                ) {
                    items(currentState.locationList) { location ->
                        LocationListItem(
                            location = location,
                            onLocationClick = onLocationClick
                        )
                    }
                }
            }

            is DisplayLocationsViewState.Error -> {}

            is DisplayLocationsViewState.Empty -> {}
            DisplayLocationsViewState.NoGroupSelected -> {}
        }
    }
}

@Composable
fun LocationListItem(
    location: Location,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onLocationClick.invoke() },
        modifier = modifier.padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = location.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )

            Text(
                text = location.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationListPaneTopBar(
    groupName: String,
    onBackClick: () -> Unit,
    layoutType: NavigationSuiteType,
) {
    if (layoutType == NavigationSuiteType.NavigationBar) {
        TopAppBar(
            title = { Text(groupName) },
            navigationIcon = {
                IconButton(onClick = { onBackClick.invoke() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = { LocationListActions() }
        )
    } else {
        TopAppBar(
            title = { Text(groupName) },
            actions = { LocationListActions() }
        )
    }
}

@Composable
private fun LocationListActions() {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = !expanded }) {
        Icon(Icons.Default.MoreVert, contentDescription = "More options")
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(Res.string.groups_screen_edit_location_option)) },
            onClick = { /* Do something... */ }
        )
        DropdownMenuItem(
            text = { Text(stringResource(Res.string.groups_screen_delete_location_option)) },
            onClick = { /* Do something... */ }
        )
    }
}
