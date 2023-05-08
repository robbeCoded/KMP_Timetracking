package de.cgi.android.util

import TimetrackingTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.X
import de.cgi.common.ErrorEntity

@Composable
fun GenericError(
    error: ErrorEntity? = null,
    dismissText: String? = null,
    onDismissAction: (() -> Unit)? = null,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = FeatherIcons.X,
            tint = Color.Red,
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = error?.message ?: "error")
        onDismissAction?.let {
            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = onDismissAction) {
                Text(text = dismissText ?: "ok")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGenericError() {
    TimetrackingTheme {
        GenericError(onDismissAction = {})
    }
}