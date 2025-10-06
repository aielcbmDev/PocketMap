package charly.baquero.pocketmap.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import org.jetbrains.compose.resources.stringResource
import pocketmap.composeapp.generated.resources.Res
import pocketmap.composeapp.generated.resources.confirm_group_deletion_dialog_cancel_button_text
import pocketmap.composeapp.generated.resources.confirm_group_deletion_dialog_confirm_button_text
import pocketmap.composeapp.generated.resources.confirm_group_deletion_dialog_text
import pocketmap.composeapp.generated.resources.confirm_group_deletion_dialog_title
import pocketmap.composeapp.generated.resources.create_group_dialog_cancel_button_text
import pocketmap.composeapp.generated.resources.create_group_dialog_confirm_button_text
import pocketmap.composeapp.generated.resources.create_group_dialog_text_field_label
import pocketmap.composeapp.generated.resources.create_group_dialog_title
import pocketmap.composeapp.generated.resources.edit_group_dialog_cancel_button_text
import pocketmap.composeapp.generated.resources.edit_group_dialog_confirm_button_text
import pocketmap.composeapp.generated.resources.edit_group_dialog_text_field_label
import pocketmap.composeapp.generated.resources.edit_group_dialog_title

@Composable
fun CreateGroupDialog(
    createGroup: (String) -> Unit,
    dismissDialog: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { dismissDialog.invoke() },
        title = { Text(stringResource(Res.string.create_group_dialog_title)) },
        text = {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text(stringResource(Res.string.create_group_dialog_text_field_label)) }
            )
        },
        confirmButton = {
            Button(onClick = { createGroup.invoke(text) }) {
                Text(stringResource(Res.string.create_group_dialog_confirm_button_text))
            }
        },
        dismissButton = {
            Button(onClick = { dismissDialog.invoke() }) {
                Text(stringResource(Res.string.create_group_dialog_cancel_button_text))
            }
        }
    )
}

@Composable
fun ConfirmGroupDeletionDialog(
    deleteGroups: () -> Unit,
    dismissDialog: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { dismissDialog.invoke() },
        title = { Text(stringResource(Res.string.confirm_group_deletion_dialog_title)) },
        text = { Text(stringResource(Res.string.confirm_group_deletion_dialog_text)) },
        confirmButton = {
            Button(onClick = { deleteGroups.invoke() }) {
                Text(stringResource(Res.string.confirm_group_deletion_dialog_confirm_button_text))
            }
        },
        dismissButton = {
            Button(onClick = { dismissDialog.invoke() }) {
                Text(stringResource(Res.string.confirm_group_deletion_dialog_cancel_button_text))
            }
        }
    )
}

@Composable
fun EditGroupDialog(
    groupName: String,
    editGroup: (String) -> Unit,
    dismissDialog: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf(groupName) }
    AlertDialog(
        onDismissRequest = { dismissDialog.invoke() },
        title = { Text(stringResource(Res.string.edit_group_dialog_title)) },
        text = {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text(stringResource(Res.string.edit_group_dialog_text_field_label)) }
            )
        },
        confirmButton = {
            Button(onClick = { editGroup.invoke(text) }) {
                Text(stringResource(Res.string.edit_group_dialog_confirm_button_text))
            }
        },
        dismissButton = {
            Button(onClick = { dismissDialog.invoke() }) {
                Text(stringResource(Res.string.edit_group_dialog_cancel_button_text))
            }
        }
    )
}
