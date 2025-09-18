package charly.baquero.pocketmap.ui.display

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import charly.baquero.pocketmap.domain.model.Location
import charly.baquero.pocketmap.ui.DisplayLocationsViewState

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
            LocationPaneTopBar(
                onBackClick = onBackClick,
                layoutType = layoutType
            )
        }
    ) { _ ->
        when (val currentState = displayLocationsViewState) {
            is DisplayLocationsViewState.Loading -> {}

            is DisplayLocationsViewState.Success -> {
                val state = rememberLazyListState()
                LazyColumn(
                    state = state,
                    modifier = modifier.fillMaxWidth(),
                    contentPadding = WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal + WindowInsetsSides.Top
                    ).asPaddingValues()
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
fun LocationPaneTopBar(
    onBackClick: () -> Unit,
    layoutType: NavigationSuiteType,
) {
    if (layoutType == NavigationSuiteType.NavigationBar) {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = { onBackClick.invoke() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }
}
