package de.cgi.components.styles

import org.jetbrains.compose.web.css.Color
import de.cgi.common.ui.ColorConstants
import org.jetbrains.compose.web.css.CSSColorValue

object CustomColors {
    val background: CSSColorValue = Color(ColorConstants.BACKGROUND.toString())
    val topAppbar: CSSColorValue = Color(ColorConstants.TOP_APPBAR.toString())
    val lightGrey: CSSColorValue = Color(ColorConstants.LIGHT_GREY.toString())
    val darkGrey: CSSColorValue = Color(ColorConstants.DARK_GREY.toString())
    val actionPrimary: CSSColorValue = Color(ColorConstants.ACTION_PRIMARY.toString())
    val actionSecondary: CSSColorValue = Color(ColorConstants.ACTION_SECONDARY.toString())
    val actionSuperWeak: CSSColorValue = Color(ColorConstants.ACTION_SUPER_WEAK.toString())
    val actionRed: CSSColorValue = Color(ColorConstants.ACTION_RED.toString())
    val itemColor: CSSColorValue = Color(ColorConstants.ITEM_COLOR.toString())
    val white: CSSColorValue = Color(ColorConstants.WHITE.toString())
    val black: CSSColorValue = Color(ColorConstants.BLACK.toString())
    val darkBlue: CSSColorValue = Color(ColorConstants.DARK_BLUE.toString())
    val cgiRed: CSSColorValue = Color(ColorConstants.CGI_RED.toString())
    val cgiPurple: CSSColorValue = Color(ColorConstants.CGI_PURPLE.toString())
    val cgiPurpleLight: CSSColorValue = Color(ColorConstants.CGI_PURPLE_LIGHT.toString())
    val lightGreen: CSSColorValue = Color(ColorConstants.LIGHT_GREEN.toString())
    val projectColorsList: List<CSSColorValue> =
        ColorConstants.PROJECT_COLORS_LIST.map { Color(it.toString()) }
}
