package charly.baquero.pocketmap.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Map
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import pocketmap.composeapp.generated.resources.Res
import pocketmap.composeapp.generated.resources.tab_groups_content_description
import pocketmap.composeapp.generated.resources.tab_groups_label
import pocketmap.composeapp.generated.resources.tab_groups_tooltip_text
import pocketmap.composeapp.generated.resources.tab_groups_tooltip_title
import pocketmap.composeapp.generated.resources.tab_map_content_description
import pocketmap.composeapp.generated.resources.tab_map_label
import pocketmap.composeapp.generated.resources.tab_map_tooltip_text
import pocketmap.composeapp.generated.resources.tab_map_tooltip_title

private const val mapRoute = "mapTab"
private const val groupsRoute = "groupsTab"

enum class BottomTab(
    val labelRes: StringResource,
    val tooltipTitle: StringResource,
    val tooltipText: StringResource,
    val contentDescription: StringResource,
    val icon: ImageVector,
    val route: String
) {
    Map(
        labelRes = Res.string.tab_map_label,
        tooltipTitle = Res.string.tab_map_tooltip_title,
        tooltipText = Res.string.tab_map_tooltip_text,
        contentDescription = Res.string.tab_map_content_description,
        icon = Icons.Outlined.Map,
        route = mapRoute
    ),
    Groups(
        labelRes = Res.string.tab_groups_label,
        tooltipTitle = Res.string.tab_groups_tooltip_title,
        tooltipText = Res.string.tab_groups_tooltip_text,
        contentDescription = Res.string.tab_groups_content_description,
        icon = Icons.Outlined.Group,
        route = groupsRoute
    );
}
