package de.cgi.android.timeentry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.cgi.common.data.model.TimeEntry
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime


@Composable
fun TimeEntryListItem(
    timeEntry: TimeEntry,
    onClick: (TimeEntry) -> Unit,
    onDeleteTimeEntry: (TimeEntry) -> Unit,
) {
    val startTime: LocalDateTime = timeEntry.startTime.toLocalDateTime()
    val endTime: LocalDateTime = timeEntry.endTime.toLocalDateTime()

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
                text = startTime.time.toString() + " - " + endTime.time.toString()
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(text = timeEntry.projectId ?: "No project ID")

        }
    }
}