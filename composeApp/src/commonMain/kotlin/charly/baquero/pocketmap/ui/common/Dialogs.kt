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

@Composable
fun CreateGroupDialog(
    createGroup: (String) -> Unit,
    dismissDialog: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { dismissDialog.invoke() },
        title = { Text("Create Group") },
        text = {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text("Introduce a group name here") }
            )
        },
        confirmButton = {
            Button(onClick = { createGroup.invoke(text) }) {
                Text("Create")
            }
        },
        dismissButton = {
            Button(onClick = { dismissDialog.invoke() }) {
                Text("Cancel")
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
        title = { Text("Delete Groups") },
        text = { Text("Are you sure that you want to delete the selected groups? This action cannot be undone.") },
        confirmButton = {
            Button(onClick = { deleteGroups.invoke() }) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(onClick = { dismissDialog.invoke() }) {
                Text("Cancel")
            }
        }
    )
}
