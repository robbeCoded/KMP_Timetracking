package de.cgi.components.styles

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun HorizontalSpacer(width: Int) {
    Div(
        attrs = Modifier
            .width(width.px)
            .toAttrs()
    )
}

@Composable
fun VerticalSpacer(height: Int){
    Div(
        attrs = Modifier
            .height(height.px)
            .toAttrs()
    )
}