package de.cgi.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.CheckCircle
import compose.icons.feathericons.RefreshCcw
import compose.icons.feathericons.Trash
import de.cgi.android.ui.theme.LocalColor

@Composable
fun AddEditButtonSection(
    edit: Boolean,
    onSubmit: () -> Unit,
    onUpdate: () -> Unit,
    onDelete: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Button(
        onClick = {
            if (edit) {
                onUpdate()
            } else {
                onSubmit()
            }
            onNavigateBack()
        },
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = LocalColor.current.actionPrimary)
    ) {
        Icon(
            if (edit) {
                FeatherIcons.RefreshCcw
            } else {
                FeatherIcons.CheckCircle
            },
            contentDescription = if (edit) "Update" else "Submit",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(if (edit) "Update" else "Submit")
    }
    if (edit) {
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = {
                onDelete()
                onNavigateBack()
            },
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LocalColor.current.actionRed)
        ) {
            Icon(
                FeatherIcons.Trash,
                contentDescription = "Delete",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("Delete")
        }
    }

}
