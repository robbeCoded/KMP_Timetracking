package de.cgi.common.dashboard

import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate


expect class DashboardViewModel {

    val listState: StateFlow<DashboardDataState>
    val projectSummaries: StateFlow<List<DashboardDataPerProject>>

    fun getTimeEntries()

    fun updateSelectedDateAndReloadMinus()

    fun updateSelectedDateAndReloadPlus()

    fun getSelectedDate(): LocalDate

}
