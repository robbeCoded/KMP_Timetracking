package de.cgi.components.widgets

import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.base
import com.varabyte.kobweb.silk.components.style.hover
import com.varabyte.kobweb.silk.components.style.placeholderShown
import org.jetbrains.compose.web.css.*

private val INTERACT_COLOR = Color.rgb(0x00, 0x70, 0xf3)
val BORDER_COLOR = Color.rgb(0xea, 0xea, 0xea)

/** Common styles for all todo widgets */
val InputFieldStyleSmall by ComponentStyle.base {
    Modifier
        .width(200.px)
        .height(30.px)
        .border(1.px, LineStyle.Solid, Colors.Black)
        .borderRadius(10.px)
        .textDecorationLine(TextDecorationLine.None)
}
val InputFieldStyleBig by ComponentStyle.base {
    Modifier
        .width(450.px)
        .height(30.px)
        .border(1.px, LineStyle.Solid, Colors.Black)
        .borderRadius(10.px)
        .textDecorationLine(TextDecorationLine.None)
}

/** Styles for the bordered, outer container (the form component has an inner and outer layer) */
val AuthContainerStyle by ComponentStyle.base {

    Modifier
        .textAlign(TextAlign.Left)/*
        .margin(0.5.cssRem)
        .border(1.px, LineStyle.Solid, BORDER_COLOR)
        .display(DisplayStyle.Flex)

        .alignItems(AlignItems.Center)*/
}

/** Styles for the text parts of todo widgets */
val TodoTextStyle by ComponentStyle.base {
    Modifier
        .padding(1.5.cssRem)
        .fontSize(1.25.cssRem)
        // We use "A" tags for accessibility, but we want our colors to come from our container
        .color(Color("inherit"))
}

/** Styles for the input element which handles user input */
val TodoInputStyle by ComponentStyle {
    base {
        Modifier
            .fillMaxWidth()
            .backgroundColor(Colors.Transparent)
            .border(0.px)
    }

    placeholderShown {
        Modifier.fontStyle(FontStyle.Italic)
    }
}

/** Styles for mouse interaction with todo widgets */
val TodoClickableStyle by ComponentStyle {
    hover {
        Modifier
            .color(INTERACT_COLOR)
            .cursor(Cursor.Pointer)
            .borderColor(INTERACT_COLOR)
            .textDecorationLine(TextDecorationLine.LineThrough)
    }
}