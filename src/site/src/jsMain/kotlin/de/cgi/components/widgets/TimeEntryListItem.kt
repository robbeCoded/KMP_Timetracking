package de.cgi.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaPen
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.components.styles.Theme
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalTime
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.kodein.di.compose.localDI
import org.kodein.di.instance


@Composable
fun TimeEntryListItem(
    timeEntry: TimeEntry,
    onClick: (TimeEntry) -> Unit,
) {
    val di = localDI()
    val startTime: LocalTime = timeEntry.startTime.toLocalTime()
    val endTime: LocalTime = timeEntry.endTime.toLocalTime()
    val duration = LocalTime.fromSecondOfDay(endTime.toSecondOfDay() - startTime.toSecondOfDay())

    val projectMapProvider: ProjectMapProvider by di.instance()
    val color = projectMapProvider.getProjectColorById(timeEntry.projectId) ?: Theme.ItemColor.hex


    Box(
        modifier = Modifier
            .margin(8.px)
            .padding(8.px)
            .borderRadius(10.px)
            .fillMaxWidth()
            .styleModifier { property("background-color", color) }
            .onClick { onClick(timeEntry) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val description = if (timeEntry.description.isNullOrBlank()) {
                "Keine Beschreibung"
            } else {
                timeEntry.description
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Div(
                    Modifier
                        .fontWeight(FontWeight.SemiBold)
                        .styleModifier {
                            property("word-break", "break-word")
                            property("overflow-wrap", "break-word")
                        }
                        .toAttrs()
                ) {
                    Text(description ?: "Keine Beschreibung")
                }
                FaPen()
            }

            val projectText =
                timeEntry.projectId?.let { projectMapProvider.getProjectNameById(it) }
                    ?: "Internal"

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("$startTime - $endTime")
                Div(
                    Modifier
                        .fontWeight(FontWeight.SemiBold)
                        .toAttrs()
                ) {
                    Text(duration.toString())
                }
            }

            Text(projectText)

        }
    }
}