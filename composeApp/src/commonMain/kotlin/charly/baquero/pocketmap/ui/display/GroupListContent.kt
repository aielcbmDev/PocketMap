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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import charly.baquero.pocketmap.domain.model.Group

@Composable
fun GroupListPane(
    displayGroupState: DisplayGroupViewState.Success,
    onGroupClick: (Group) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberLazyListState()
    LazyColumn(
        state = state,
        modifier = modifier.fillMaxWidth(),
        contentPadding = WindowInsets.safeDrawing.only(
            WindowInsetsSides.Horizontal + WindowInsetsSides.Top
        ).asPaddingValues()
    ) {
        items(displayGroupState.groupList) { group ->
            GroupListItem(
                group = group,
                onGroupClick = onGroupClick
            )
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
