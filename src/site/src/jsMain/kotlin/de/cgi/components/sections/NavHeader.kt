package de.cgi.components.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.dom.ElementTarget
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.icons.fa.FaMoon
import com.varabyte.kobweb.silk.components.icons.fa.FaSun
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.Tooltip
import com.varabyte.kobweb.silk.components.style.*
import com.varabyte.kobweb.silk.components.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.rememberColorMode
import com.varabyte.kobweb.silk.theme.toSilkPalette
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

val NavHeaderStyle by ComponentStyle.base(extraModifiers = { SmoothColorStyle.toModifier() }) {
    Modifier
        .fillMaxHeight()
        .width(200.px)
        // Intentionally invert the header colors from the rest of the page
        .backgroundColor(colorMode.toSilkPalette().color)
}

val NavItemStyle by ComponentStyle {
    // Intentionally invert the header colors from the rest of the page
    val linkColor = colorMode.toSilkPalette().background

    base { Modifier.margin(leftRight = 15.px) }

    link { Modifier.color(linkColor) }
    visited { Modifier.color(linkColor) }
}

val NavButtonVariant by NavItemStyle.addVariant {
    base { Modifier.padding(0.px).borderRadius(50.percent) }
}

@Composable
private fun NavLink(path: String, text: String) {
    Link(path, text, NavItemStyle.toModifier(), UndecoratedLinkVariant)
}

@Composable
fun NavMenu() {
    Box(NavHeaderStyle.toModifier()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavLink("/calender/home", "Kalender")
            NavLink("/timeentry/list", "Zeiterfassung")
            NavLink("/project/list", "Projekte")
            NavLink("/dashboard", "Dashboard")
            NavLink("/settings/home", "Einstellungen")
            NavLink("/account/home", "Account")
            Spacer()
        }
    }
}