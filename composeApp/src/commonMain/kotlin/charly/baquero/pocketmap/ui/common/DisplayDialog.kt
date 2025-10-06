package charly.baquero.pocketmap.ui.common

import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.ui.DialogState

@Composable
fun DisplayDialog(
    dialogState: DialogState,
    createGroup: (String) -> Unit = {},
    deleteGroups: () -> Unit = {},
    editGroup: (String) -> Unit = {},
    onDismissDialog: () -> Unit
) {
    when (dialogState) {
        is DialogState.CreateGroup -> CreateGroupDialog(
            createGroup = { createGroup.invoke(it) },
            dismissDialog = { onDismissDialog.invoke() }
        )

        is DialogState.DeleteGroups -> ConfirmGroupDeletionDialog(
            deleteGroups = { deleteGroups.invoke() },
            dismissDialog = { onDismissDialog.invoke() }
        )

        is DialogState.EditGroup -> EditGroupDialog(
            groupName = dialogState.groupName,
            editGroup = { editGroup.invoke(it) },
            dismissDialog = { onDismissDialog.invoke() }
        )

        is DialogState.NoDialog -> { /* Do nothing */ }
    }
}
