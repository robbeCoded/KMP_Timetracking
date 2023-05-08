package de.cgi.android.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColors(
    internal val background: Color = Color(0xFFFFFDF6),
    internal val topAppbar: Color = Color(0xFFFFFCF3),
    internal val lightGrey: Color = Color(0xFFF9F9F9),
    internal val darkGrey: Color = Color(0xFFEBEBEB),
    internal val actionPrimary: Color = Color(0xFFa199ff),
    internal val actionSecondary: Color = Color(0xFFd0ccff),
    internal val actionSuperWeak: Color = Color(0xFFecebff),
    internal val actionRed: Color = Color(0xFFff4765),
    internal val itemColor: Color = Color(0xFFe4e4e4),
    internal val white: Color = Color.White,
    internal val black: Color = Color.Black,
    internal val darkBlue: Color = Color(0xFF00008B),
    internal val cgiRed: Color = Color(0xFFE31937),
    internal val cgiPurple: Color = Color(0xFF592eb2),
    internal val cgiPurpleLight: Color = Color(0xFF2b81d3),
    internal val lightGreen: Color = Color(0xFFCEFFA7),

    internal val projectColorsList: List<Color> = listOf(
        Color(0xFF4E6A53),
        Color(0xFF527A7A),
        Color(0xFF709AA3),
        Color(0xFF7088A3),
        Color(0xFF8070A3),
    )
)

val LocalColor = compositionLocalOf { CustomColors() }