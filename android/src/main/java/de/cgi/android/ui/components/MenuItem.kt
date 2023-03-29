package de.cgi.android.ui.components

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val id: MenuId,
    val title: String,
    val icon: ImageVector,
    val contentDescription: String,
)
