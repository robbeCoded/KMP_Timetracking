package de.cgi.components.styles

import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.link
import org.jetbrains.compose.web.css.px

val PageTitle by ComponentStyle {
    base {
        Modifier
            .fontSize(24.px)
            .fontWeight(FontWeight.Medium)
    }
}

val Heading1 by ComponentStyle {
    base {
        Modifier
            .fontSize(32.px)
            .fontWeight(FontWeight.Bold)
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

