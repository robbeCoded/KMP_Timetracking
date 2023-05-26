package de.cgi.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaPen
import de.cgi.common.data.model.Project
import de.cgi.common.util.format
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text


@Composable
fun ProjectListItem(
    project: Project,
    onClick: (Project) -> Unit,
) {
    val startDate: LocalDate = project.startDate.toLocalDate()
    val endDate: LocalDate = project.endDate.toLocalDate()
    val color = project.color.toString()


    Box(
        modifier = Modifier
            .margin(8.px)
            .padding(8.px)
            .borderRadius(10.px)
            .fillMaxWidth()
            .styleModifier { property("background-color", color) }
            .onClick { onClick(project) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
                Div(
                    Modifier
                        .fontWeight(FontWeight.SemiBold)
                        .toAttrs()
                ) {
                    Text(project.name)
                }
                FaPen()
            }

            Text(project.description ?: "Keine Beschreibung")
            Spacer()
            Text(
                "${startDate.format()} - ${endDate.format()}"
            )

        }
    }
}