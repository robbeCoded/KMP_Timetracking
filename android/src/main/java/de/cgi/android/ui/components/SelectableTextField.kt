package de.cgi.android.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.cgi.android.ui.theme.LocalColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectableTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    onClick: (() -> Unit)? = null,
) {
    val textFieldModifier = Modifier
        .fillMaxWidth()
        .border(
            width = 1.dp,
            shape = RoundedCornerShape(10.dp),
            color = LocalColor.current.black
        )
        .clickable { if(onClick != null) {onClick()} }

    Column(modifier = modifier.wrapContentSize()) {
        Text(text = label)
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            modifier = textFieldModifier,
            enabled = onClick == null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = LocalColor.current.black,
                unfocusedBorderColor = LocalColor.current.black,
                textColor = LocalColor.current.black,
                disabledTextColor = LocalColor.current.black
            ),
            shape = RoundedCornerShape(10.dp),
        )
    }
}

