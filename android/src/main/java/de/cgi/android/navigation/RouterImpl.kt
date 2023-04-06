package de.cgi.android.navigation

import androidx.navigation.NavHostController
import de.cgi.android.auth.AuthScreenRoute
import de.cgi.android.navigation.timeentry.AddEditTimeEntryRoute
import de.cgi.android.navigation.timeentry.TimeEntryListRoute
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

    override fun showTimeEntryDetails(timeEntry: TimeEntry?) {
        navigationController.navigate(
            AddEditTimeEntryRoute.buildAddEditTimeEntryRoute(timeEntry?.id))
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
        TODO("Not yet implemented")
    }

    override fun navigationUp(): Boolean {
        TODO("Not yet implemented")
    }
}