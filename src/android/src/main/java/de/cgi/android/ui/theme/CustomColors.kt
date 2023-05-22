package de.cgi.android.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import de.cgi.common.ui.ColorConstants

data class CustomColors(
    val background: Color = Color(ColorConstants.BACKGROUND),
    val topAppbar: Color = Color(ColorConstants.TOP_APPBAR),
    val lightGrey: Color = Color(ColorConstants.LIGHT_GREY),
    val darkGrey: Color = Color(ColorConstants.DARK_GREY),
    val actionPrimary: Color = Color(ColorConstants.ACTION_PRIMARY),
    val actionSecondary: Color = Color(ColorConstants.ACTION_SECONDARY),
    val actionSuperWeak: Color = Color(ColorConstants.ACTION_SUPER_WEAK),
    val actionRed: Color = Color(ColorConstants.ACTION_RED),
    val itemColor: Color = Color(ColorConstants.ITEM_COLOR),
    val white: Color = Color(ColorConstants.WHITE),
    val black: Color = Color(ColorConstants.BLACK),
    val darkBlue: Color = Color(ColorConstants.DARK_BLUE),
    val cgiRed: Color = Color(ColorConstants.CGI_RED),
    val cgiPurple: Color = Color(ColorConstants.CGI_PURPLE),
    val cgiPurpleLight: Color = Color(ColorConstants.CGI_PURPLE_LIGHT),
    val lightGreen: Color = Color(ColorConstants.LIGHT_GREEN),
    val projectColorsList: List<Color> = ColorConstants.PROJECT_COLORS_LIST.map { Color(it) }
)

val LocalColor = compositionLocalOf { CustomColors() }