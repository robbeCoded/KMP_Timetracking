package de.cgi.android.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColors (
    internal val background: Color = Color(0xFFFFFDF6),
    internal val topAppbar: Color = Color(0xFFFFFCF3),
    internal val lightGrey: Color = Color(0xFFEBEBEB),
    internal val darkGrey: Color = Color(0xFFF9F9F9),
    internal val actionPrimary: Color = Color(0xFFa199ff),
    internal val actionSecondary: Color = Color(0xFFd0ccff),
    internal val actionRed: Color = Color(0xFFff4765),
    internal val itemColor: Color = Color(0xFFe4e4e4),
    internal val white: Color = Color.White,
    internal val black: Color = Color.Black
)

val LocalColor = compositionLocalOf { CustomColors() }