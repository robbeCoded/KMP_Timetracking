package de.cgi.android.timeentry.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.cgi.android.util.AsyncData
import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.ProjectNameProvider
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalTime
import org.koin.androidx.compose.get


@Composable
fun TimeEntryListItem(
    timeEntry: TimeEntry,
    onClick: (TimeEntry) -> Unit,
) {
    val startTime: LocalTime = timeEntry.startTime.toLocalTime()
    val endTime: LocalTime = timeEntry.endTime.toLocalTime()
    val projectNameProvider = get<ProjectNameProvider>()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onClick(timeEntry) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(text = timeEntry.description ?: "Keine Beschreibung")

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "$startTime - $endTime"
            )

            Spacer(modifier = Modifier.height(8.dp))

            val projectText =
                timeEntry.projectId?.let { projectNameProvider.getProjectNameById(it) }
                    ?: "Internal"

            Text(text = projectText)

        }
    }
}