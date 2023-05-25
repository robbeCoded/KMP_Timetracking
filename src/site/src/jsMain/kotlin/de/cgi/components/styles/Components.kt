package de.cgi.components.styles

import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.hover
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px


val MainButtonStyle by ComponentStyle {
    base {
        Modifier
            .width(100.px)
            .transition(CSSTransition(property = "width", duration = 200.ms))
    }
    hover {
        Modifier.width(120.px)
    }
}

val PageHeaderStyle by ComponentStyle {
    base {
        Modifier
            .padding(8.px)
            .fillMaxWidth()
            .height(10.percent)
            .backgroundColor(Theme.TopAppbar.rgb)
            .boxShadow(3.px, 2.px, 3.px, 0.px, Theme.Grey5.rgb)
    }
}