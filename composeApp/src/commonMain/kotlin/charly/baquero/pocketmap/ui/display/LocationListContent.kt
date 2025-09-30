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
import androidx.compose.material.icons.outlined.EditLocation
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import charly.baquero.pocketmap.domain.model.Location
import charly.baquero.pocketmap.ui.LocationsViewState
import charly.baquero.pocketmap.ui.common.IconButtonWithRichTooltip
import org.jetbrains.compose.resources.stringResource
import pocketmap.composeapp.generated.resources.Res
import pocketmap.composeapp.generated.resources.location_screen_delete_location_option
import pocketmap.composeapp.generated.resources.location_screen_edit_location_tooltip_text
import pocketmap.composeapp.generated.resources.location_screen_edit_location_tooltip_title
import pocketmap.composeapp.generated.resources.more_options

@Composable
fun LocationListPane(
    locationsViewState: LocationsViewState,
    onBackClick: () -> Unit,
    onLocationClick: (Location) -> Unit,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            LocationListPaneTopBar(
                groupName = (locationsViewState as? LocationsViewState.Success)?.groupName
                    ?: "",
                onBackClick = onBackClick,
                canNavigateBack = canNavigateBack
            )
        }
    ) { padding ->
        when (val currentState = locationsViewState) {
            is LocationsViewState.Loading -> {}

            is LocationsViewState.Success -> {
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

            is LocationsViewState.Error -> {}

            is LocationsViewState.Empty -> {}
            LocationsViewState.NoGroupSelected -> {}
        }
    }
}

@Composable
fun LocationListItem(
    location: Location,
    onLocationClick: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onLocationClick.invoke(location) },
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
    canNavigateBack: Boolean,
) {
    if (canNavigateBack) {
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
    IconButtonWithRichTooltip(
        tooltipTitle = stringResource(Res.string.location_screen_edit_location_tooltip_title),
        tooltipText = stringResource(Res.string.location_screen_edit_location_tooltip_text),
        imageVector = Icons.Outlined.EditLocation,
        contentDescription = stringResource(Res.string.location_screen_edit_location_tooltip_title),
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
            text = { Text(stringResource(Res.string.location_screen_delete_location_option)) },
            onClick = { /* Do something... */ }
        )
    }
}
