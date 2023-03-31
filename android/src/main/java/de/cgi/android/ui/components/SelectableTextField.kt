package de.cgi.android.timetracking.addedittimeentry.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectableTextField(
    modifier: Modifier = Modifier,
    textValue: String,
    label: String,
    onClick: () -> Unit,
) {
    TextField(
        value = textValue,
        onValueChange = { },
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        enabled = false
    )
}