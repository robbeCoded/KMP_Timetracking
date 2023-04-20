package de.cgi.android.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import de.cgi.android.ui.theme.LocalColor
import de.cgi.android.ui.theme.LocalTypography

@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "Timetracking", style = LocalTypography.current.headlineSmall) },
        backgroundColor = LocalColor.current.topAppbar,
        contentColor = LocalColor.current.black,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Toggle drawer")
            }
        }
    )

}