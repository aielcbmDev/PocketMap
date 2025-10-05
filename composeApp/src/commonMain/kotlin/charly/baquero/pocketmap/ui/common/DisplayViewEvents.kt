package charly.baquero.pocketmap.ui.common

import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.ui.DialogState

@Composable
fun DisplayDialog(
    dialogState: DialogState?,
    createGroup: (String) -> Unit,
    onDismissCreateGroupDialog: () -> Unit
) {
    dialogState?.let { it ->
        when (it) {
            is DialogState.CreateGroup -> CreateGroupDialog(
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
