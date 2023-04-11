package de.cgi.android.navigation

import androidx.navigation.NavHostController
import de.cgi.android.auth.AuthScreenRoute
import de.cgi.android.timeentry.TimeEntryAddRoute
import de.cgi.android.timeentry.TimeEntryEditRoute
import de.cgi.android.timeentry.TimeEntryListRoute
import de.cgi.common.data.model.TimeEntry

class RouterImpl(
    private val navigationController: NavHostController
) : Router {
    override fun showAuth() {
        navigationController.navigate(AuthScreenRoute.route) {
            popUpTo(0)
        }
    }

    override fun showTimeEntryList() {
        navigationController.navigate(TimeEntryListRoute.route) {
            popUpTo(AuthScreenRoute.route) {
                inclusive = true
                saveState = false
            }
        }
    }

    override fun showTimeEntryEdit(timeEntry: TimeEntry) {
        navigationController.navigate(
            TimeEntryEditRoute.buildAddEditTimeEntryRoute(timeEntry.id))
    }

    override fun showTimeEntryAdd() {
        navigationController.navigate(TimeEntryAddRoute.route)
    }

    override fun showProjectList() {
        TODO("Not yet implemented")
    }

    override fun showProjectDetails(id: String?) {
        TODO("Not yet implemented")
    }

    override fun showSettings() {
        TODO("Not yet implemented")
    }

    override fun back() {
        navigationController.popBackStack()
    }

    override fun navigationUp(): Boolean {
        return navigationController.navigateUp()
    }
}