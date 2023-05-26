package de.cgi.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowRightToBracket
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleCheck
import com.varabyte.kobweb.silk.components.icons.fa.FaTrash
import com.varabyte.kobweb.silk.components.style.toAttrs
import de.cgi.components.styles.Heading3
import de.cgi.components.styles.HorizontalSpacer
import de.cgi.components.styles.Theme
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text


@Composable
fun AddEditFormButton(
    onClick: () -> Unit,
    text: String,
) {
    val color = when (text) {
        "Update" -> Theme.ActionPrimary.rgb
        "Submit" -> Theme.ActionPrimary.rgb
        "Sign In" -> Theme.ActionPrimary.rgb
        "Sign Up" -> Theme.ActionPrimary.rgb
        "Delete" -> Theme.ActionRed.rgb
        else -> Theme.LightGrey.rgb
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .height(40.px)
                .width(80.percent)
                .padding(8.px)
                .borderRadius(10.px)
                .backgroundColor(color)
                .onClick { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            when (text) {
                "Update" -> FaCircleCheck()
                "Submit" -> FaCircleCheck()
                "Sign In" -> FaArrowRightToBracket()
                "Sign Up" -> FaArrowRightToBracket()
                "Delete" -> FaTrash()
            }
            HorizontalSpacer(16)
            Div(Heading3.toAttrs()) { Text(text) }

        }

    }
}
