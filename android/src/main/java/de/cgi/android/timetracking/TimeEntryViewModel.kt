package de.cgi.android.timetracking

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.android.TimeEntryState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.TimeEntryRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class TimeEntryViewModel(
    private val timeEntryRepository: TimeEntryRepository,
    private val prefs: SharedPreferences,
) : ViewModel(){

    var state by mutableStateOf(TimeEntryState())

    private val resultChannel = Channel<List<TimeEntry>>()
    val timeEntries = resultChannel.receiveAsFlow()

    init {
        receive()
    }

    private fun receive() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val token = prefs.getString("jwt", null) ?: ""
            val result = timeEntryRepository.getTimeEntries(token, false) //TODO wann passiert automatischer reload??
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}