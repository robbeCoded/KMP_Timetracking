package de.cgi.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import de.cgi.common.projects.ProjectEditViewModel
import de.cgi.components.styles.Theme
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px


@Composable
fun ColorSelection(
    colorChanged: (String) -> Unit,
    selectedColor: String,
) {


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val projectColorList = listOf(
            Theme.ProjectColor1,
            Theme.ProjectColor2,
            Theme.ProjectColor3,
            Theme.ProjectColor4,
            Theme.ProjectColor5
        )
        projectColorList.forEach { color ->
            if (selectedColor == color.hex) {
                Box(
                    modifier = Modifier
                        .height(50.px)
                        .width(50.px)
                        .border(3.px, LineStyle.Solid, Theme.ActionPrimary.rgb)
                        .backgroundColor(color.rgb)
                        .borderRadius(10.px)
                        .onClick {
                            colorChanged(color.hex)
                        })
            } else {
                Box(
                    modifier = Modifier
                        .height(50.px)
                        .width(50.px)
                        .border(1.px, LineStyle.Solid, Theme.Black.rgb)
                        .backgroundColor(color.rgb)
                        .borderRadius(10.px)
                        .onClick {
                            colorChanged(color.hex)
                        })
            }

        }
    }
}