package de.cgi.components.styles

import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import org.jetbrains.compose.web.css.px

val PageTitle by ComponentStyle {
    base {
        Modifier
            .fontSize(24.px)
            .fontWeight(FontWeight.Medium)
    }
}

val AuthHeading by ComponentStyle {
    base {
        Modifier
            .fontSize(64.px)
            .fontWeight(FontWeight.Bold)
            .color(Theme.CgiRed.rgb)
    }
}

val Heading1 by ComponentStyle {
    base {
        Modifier
            .fontSize(32.px)
            .fontWeight(FontWeight.Bold)
            .color(Theme.CgiRed.rgb)
    }
}

val Heading2 by ComponentStyle {
    base {
        Modifier
            .fontSize(24.px)
            .fontWeight(FontWeight.Bold)
    }
}

val Heading3 by ComponentStyle {
    base {
        Modifier
            .fontSize(18.px)
            .fontWeight(FontWeight.Medium)
    }
}
val Label by ComponentStyle {
    base {
        Modifier
            .fontSize(14.px)
            .fontWeight(FontWeight.Light)
    }
}

