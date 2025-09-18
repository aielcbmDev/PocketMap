package charly.baquero.pocketmap.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Map
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import pocketmap.composeapp.generated.resources.Res
import pocketmap.composeapp.generated.resources.tab_groups
import pocketmap.composeapp.generated.resources.tab_map

private const val mapRoute = "mapTab"
private const val groupsRoute = "groupsTab"

enum class BottomTab(
    val labelRes: StringResource,
    val icon: ImageVector,
    val route: String
) {
    Map(Res.string.tab_map, Icons.Outlined.Map, mapRoute),
    Groups(Res.string.tab_groups, Icons.Outlined.Group, groupsRoute);
}
