package de.cgi.android.timetracking

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.cgi.common.data.model.TimeEntry

@Composable
fun TimeEntryListItem(timeEntry: TimeEntry) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(text = timeEntry.description ?: "Keine Beschreibung")

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = timeEntry.startTime + " - "
                        + timeEntry.endTime
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(text = timeEntry.projectId ?: "No project ID")

        }
    }
}