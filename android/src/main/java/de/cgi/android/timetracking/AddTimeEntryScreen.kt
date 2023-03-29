package de.cgi.android.timetracking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.cgi.android.destinations.TimeEntryScreenDestination
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddTimeEntryScreen(
    navigator: DestinationsNavigator,
    viewModel: AddTimeEntryViewModel = getViewModel<AddTimeEntryViewModel>()
) {
    val state = viewModel.state
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Day input field
        OutlinedTextField(
            value = state.day,
            onValueChange = { viewModel.onEvent(AddTimeEntryUiEvent.TimeEntryDayChanged(it)) },
            label = { Text(text = "Day") },
            )


        // Start time input field
        OutlinedTextField(
            value = state.start,
            onValueChange = { viewModel.onEvent(AddTimeEntryUiEvent.TimeEntryStartChanged(it)) },
            label = { Text("Start Time") },

            )

        // End time input field
        OutlinedTextField(
            value = state.end,
            onValueChange = { viewModel.onEvent(AddTimeEntryUiEvent.TimeEntryEndChanged(it)) },
            label = { Text("End Time") },

            )

        // Project input field
        OutlinedTextField(
            value = state.project,
            onValueChange = { viewModel.onEvent(AddTimeEntryUiEvent.TimeEntryProjectChanged(it)) },
            label = { Text("Project") },

            )

        // Description input field
        OutlinedTextField(
            value = state.description,
            onValueChange = { viewModel.onEvent(AddTimeEntryUiEvent.TimeEntryDescriptionChanged(it)) },
            label = { Text("Description") },

            )

        // Submit button
        Button(
            onClick = {
                viewModel.onEvent(AddTimeEntryUiEvent.newTimeEntry)
                navigator.navigate(TimeEntryScreenDestination)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Submit")
        }

        // Loading indicator
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
