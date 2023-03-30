package de.cgi.android.timetracking.newTimeEntry

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.TimeEntryResponse
import de.cgi.common.repository.TimeEntryRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class AddTimeEntryViewModel (
    private val timeEntryRepository: TimeEntryRepository,
    private val prefs: SharedPreferences,
    ) : ViewModel(){

        var state by mutableStateOf(AddTimeEntryState())


        private val resultChannel = Channel<AuthResult<TimeEntryResponse?>>()
        val authResult = resultChannel.receiveAsFlow()


        fun onEvent(event: AddTimeEntryUiEvent) {
            when(event){
                //Sign in
                is AddTimeEntryUiEvent.TimeEntryDayChanged -> {
                    state = state.copy(day = event.value)
                }
                is AddTimeEntryUiEvent.TimeEntryDurationChanged -> {
                    state = state.copy(duration = event.value)
                }
                is AddTimeEntryUiEvent.TimeEntryStartChanged -> {
                    state = state.copy(start = event.value)
                }
                is AddTimeEntryUiEvent.TimeEntryEndChanged -> {
                    state = state.copy(end = event.value)
                }
                is AddTimeEntryUiEvent.TimeEntryProjectChanged -> {
                    state = state.copy(project = event.value)
                }
                is AddTimeEntryUiEvent.TimeEntryDescriptionChanged -> {
                    state = state.copy(description = event.value)
                }
                is AddTimeEntryUiEvent.newTimeEntry -> {
                    addTimeEntry()
                }
            }
        }
        private fun addTimeEntry() {
            viewModelScope.launch {
                state = state.copy(isLoading = true)
                val token = prefs.getString("jwt", null) ?: ""
                val userId = prefs.getString("userId", null) ?: ""
                val startTime = state.day.toString().replace("/", "-") + "T" + state.start.toString()
                val endTime = state.day.toString().replace("/", "-") + "T" + state.end.toString()
                val result = timeEntryRepository.newTimeEntry(
                    startTime = startTime,
                    endTime = endTime,
                    userId = userId,
                    description = state.description,
                    projectId = state.project,
                    token = token,
                )
                resultChannel.send(result)
                state = state.copy(isLoading = false)
            }
        }
}

