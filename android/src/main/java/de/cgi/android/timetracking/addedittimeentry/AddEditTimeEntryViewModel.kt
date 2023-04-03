package de.cgi.android.timetracking.addedittimeentry

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.TimeEntryRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class AddEditTimeEntryViewModel (
    private val timeEntryRepository: TimeEntryRepository,
    private val prefs: SharedPreferences,
    ) : ViewModel(){

        var state by mutableStateOf(AddEditTimeEntryState())


        private val resultChannel = Channel<TimeEntry?>()
        val result = resultChannel.receiveAsFlow()


        fun onEvent(event: AddEditTimeEnryUIEvent) {
            when(event){
                //Sign in
                is AddEditTimeEnryUIEvent.TimeEntryDayChanged -> {
                    state = state.copy(day = event.value)
                }
                is AddEditTimeEnryUIEvent.TimeEntryDurationChanged -> {
                    state = state.copy(duration = event.value)
                }
                is AddEditTimeEnryUIEvent.TimeEntryStartChanged -> {
                    state = state.copy(start = event.value)
                }
                is AddEditTimeEnryUIEvent.TimeEntryEndChanged -> {
                    state = state.copy(end = event.value)
                }
                is AddEditTimeEnryUIEvent.TimeEntryProjectChanged -> {
                    state = state.copy(project = event.value)
                }
                is AddEditTimeEnryUIEvent.TimeEntryDescriptionChanged -> {
                    state = state.copy(description = event.value)
                }
                is AddEditTimeEnryUIEvent.SubmitTimeEntry -> {
                    addTimeEntry()
                }
                is AddEditTimeEnryUIEvent.DeleteTimeEntry -> {
                    deleteTimeEntry(id = event.id)
                }
                is AddEditTimeEnryUIEvent.EditTimeEntry -> {
                    getTimeEntry(id = event.id)
                }
            }
        }
        private fun addTimeEntry() {
            viewModelScope.launch {
                state = state.copy(isLoading = true)
                val token = prefs.getString("jwt", null) ?: ""
                val userId = prefs.getString("userId", null) ?: ""
                println(state.start.toString())
                println(state.end.toString())
                val startTime = state.day.toString().replace("/", "-") + "T" + state.start.toString()
                println(startTime)

                val endTime = state.day.toString().replace("/", "-") + "T" + state.end.toString()
                println(endTime)
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

        private fun getTimeEntry(id: String) {
            viewModelScope.launch {
                state = state.copy(isLoading = true)
                val token = prefs.getString("jwt", null) ?: "" //TODO handle case were token is empty
                val result = timeEntryRepository.getTimeEntryById(id = id, token = token, forceReload = false)
                resultChannel.send(result)
                state = state.copy(project = result?.projectId ?: "")
                state = state.copy(description = result?.description ?: "")
                state = state.copy(isLoading = false)
            }
        }

        private fun deleteTimeEntry(id: String) {
            viewModelScope.launch {
                val token = prefs.getString("jwt", null) ?: ""
                timeEntryRepository.deleteTimeEntry(id = id, token = token)
            }
        }
}

