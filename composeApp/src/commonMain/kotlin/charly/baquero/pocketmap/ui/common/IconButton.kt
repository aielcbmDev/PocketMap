package charly.baquero.pocketmap.ui.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconButtonWithTooltip(
    modifier: Modifier = Modifier,
    tooltipText: String,
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip { Text(tooltipText) }
        },
        state = rememberTooltipState()
    ) {
        IconButton(onClick = { onClick.invoke() }) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconButtonWithRichTooltip(
    modifier: Modifier = Modifier,
    tooltipTitle: String,
    tooltipText: String,
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        tooltip = {
            RichTooltip(
                title = { Text(tooltipTitle) }
            ) {
                Text(tooltipText)
            }
        },
        state = rememberTooltipState()
    ) {
        IconButton(onClick = { onClick.invoke() }) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }
    }
}
