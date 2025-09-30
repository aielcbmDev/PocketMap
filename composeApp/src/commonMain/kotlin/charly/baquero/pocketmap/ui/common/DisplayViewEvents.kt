package charly.baquero.pocketmap.ui.common

import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.ui.ViewEvent

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
