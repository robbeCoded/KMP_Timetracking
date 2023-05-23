package de.cgi.android.util

import androidx.compose.ui.graphics.Color

fun colorToString(color: Color): String {
    val red = (color.red * 255).toInt()
    val green = (color.green * 255).toInt()
    val blue = (color.blue * 255).toInt()

    return "#%02X%02X%02X".format(red, green, blue)
}

fun stringToColor(colorString: String): Color {
    return if(colorString.isNotEmpty()){
        Color(android.graphics.Color.parseColor(colorString))
    }
    else Color(0)
}