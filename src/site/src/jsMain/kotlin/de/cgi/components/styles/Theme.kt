package de.cgi.components.styles

import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb

enum class Theme(
    val hex: String,
    val rgb: CSSColorValue
) {
    Primary(hex = "#00A78E", rgb = rgb(r = 0, g = 167, b = 142)),
    Secondary(hex = "#121D34", rgb = rgb(r = 18, g = 29, b = 52)),


    Background(hex = "#FFFDF6", rgb = rgb(r = 255, g = 253, b = 246)),
    TopAppbar(hex = "#FFFCF3", rgb = rgb(r = 255, g = 252, b = 243)),
    LightGrey(hex = "#F9F9F9", rgb = rgb(r = 249, g = 249, b = 249)),
    DarkGrey(hex = "#EBEBEB", rgb = rgb(r = 235, g = 235, b = 235)),
    ActionPrimary(hex = "#A199FF", rgb = rgb(r = 161, g = 153, b = 255)),
    ActionSecondary(hex = "#D0CCFF", rgb = rgb(r = 208, g = 204, b = 255)),
    ActionSuperWeak(hex = "#ECEBFF", rgb = rgb(r = 236, g = 235, b = 255)),
    ActionRed(hex = "#FF4765", rgb = rgb(r = 255, g = 71, b = 101)),
    ItemColor(hex = "#E4E4E4", rgb = rgb(r = 228, g = 228, b = 228)),
    White(hex = "#FFFFFF", rgb = rgb(r = 255, g = 255, b = 255)),
    Black(hex = "#000000", rgb = rgb(r = 0, g = 0, b = 0)),
    Grey1(hex = "#282828", rgb = rgb(r = 40, g = 40, b = 40)),
    Grey2(hex = "#303030", rgb = rgb(r = 48, g = 48, b = 48)),
    Grey3(hex = "#383838", rgb = rgb(r = 56, g = 56, b = 56)),
    Grey4(hex = "#404040", rgb = rgb(r = 64, g = 64, b = 64)),
    Grey5(hex = "#484848", rgb = rgb(r = 72, g = 72, b = 72)),
    DarkBlue(hex = "#00008B", rgb = rgb(r = 0, g = 0, b = 139)),
    CgiRed(hex = "#E31937", rgb = rgb(r = 227, g = 25, b = 55)),
    CgiPurple(hex = "#592EB2", rgb = rgb(r = 89, g = 46, b = 178)),
    CgiPurpleLight(hex = "#2B81D3", rgb = rgb(r = 43, g = 129, b = 211)),
    LightGreen(hex = "#CEFFA7", rgb = rgb(r = 206, g = 255, b = 167)),

    ProjectColor1(hex = "#BB7D8B", rgb = rgb(r = 187, g = 125, b = 139)),
    ProjectColor2(hex = "#D0B8C7", rgb = rgb(r = 208, g = 184, b = 199)),
    ProjectColor3(hex = "#D2CAD5", rgb = rgb(r = 210, g = 202, b = 213)),
    ProjectColor4(hex = "#DFDAE2", rgb = rgb(r = 223, g = 218, b = 226)),
    ProjectColor5(hex = "#F5F5F6", rgb = rgb(r = 245, g = 245, b = 246))


}
