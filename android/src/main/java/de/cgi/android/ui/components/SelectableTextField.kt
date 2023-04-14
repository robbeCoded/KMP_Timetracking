package de.cgi.android.ui.components

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
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    onClick: () -> Unit,
) {
    TextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        enabled = false,
    )
}