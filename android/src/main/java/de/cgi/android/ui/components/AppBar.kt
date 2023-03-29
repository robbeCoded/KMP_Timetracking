package de.cgi.android.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.cgi.R
import de.cgi.android.ui.theme.LocalColor

@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit,
    title: String,
) {
    TopAppBar(
        title = { Text(text = title) },
        backgroundColor = LocalColor.current.topAppbar,
        contentColor = LocalColor.current.black,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Toggle drawer")
            }
        }
    )

}