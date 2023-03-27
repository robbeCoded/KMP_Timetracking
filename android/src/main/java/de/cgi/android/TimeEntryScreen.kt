package de.cgi.android

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import de.cgi.android.viewmodel.TimeEntryViewModel
import de.cgi.common.data.model.responses.TimeEntryResponse
import org.koin.androidx.compose.getViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun TimeEntryScreen(
    viewModel: TimeEntryViewModel = getViewModel<TimeEntryViewModel>()
) {

    val timeEntries by viewModel.timeEntries.collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Time Entries") })
        }
    ) {
        if (timeEntries.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 2.dp
                )
            }
        } else {
            LazyColumn {
                items(timeEntries) { timeEntry ->
                    TimeEntryListItem(timeEntry)
                }
            }
        }
    }
}

@Composable
fun TimeEntryListItem(timeEntry: TimeEntryResponse) {

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
                text = timeEntry.startTime.time.toString() + " - "
                        + timeEntry.endTime.time.toString()
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(text = timeEntry.projectId ?: "No project ID")

        }
    }
}