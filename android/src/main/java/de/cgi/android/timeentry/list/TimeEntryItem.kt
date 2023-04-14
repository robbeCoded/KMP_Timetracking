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
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalTime


@Composable
fun TimeEntryListItem(
    timeEntry: TimeEntry,
    onClick: (TimeEntry) -> Unit,
    onGetProjectMap: () -> ResultState<Map<String, String>?>
) {
    val startTime: LocalTime = timeEntry.startTime.toLocalTime()
    val endTime: LocalTime = timeEntry.endTime.toLocalTime()
    val projectMap = onGetProjectMap()
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

            val projectText = when (projectMap) {
                is ResultState.Success -> projectMap.data?.get(timeEntry.projectId) ?: "No Project assigned"
                else -> "No Project assigned"
            }

            Text(text = projectText)

        }
    }
}