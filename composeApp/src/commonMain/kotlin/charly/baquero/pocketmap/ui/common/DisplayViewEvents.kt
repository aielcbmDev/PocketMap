package charly.baquero.pocketmap.ui.common

import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.ui.ViewState

@Composable
fun DisplayViewEvent(
    viewState: ViewState?,
    createGroup: (String) -> Unit,
    onDismissCreateGroupDialog: () -> Unit
) {
    viewState?.let { it ->
        when (it) {
            is ViewState.CreateGroupDialog -> CreateGroupDialog(
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
