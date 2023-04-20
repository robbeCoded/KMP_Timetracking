package de.cgi.android.navigation

import androidx.navigation.NavHostController
import de.cgi.android.account.AccountScreenRoute
import de.cgi.android.auth.SignInScreenRoute
import de.cgi.android.auth.SignUpScreenRoute
import de.cgi.android.calender.CalenderScreenRoute
import de.cgi.android.dashboard.*
import de.cgi.android.projects.ProjectAddRoute
import de.cgi.android.projects.ProjectEditRoute
import de.cgi.android.projects.ProjectListRoute
import de.cgi.android.settings.SettingsHomeRoute
import de.cgi.android.timeentry.TimeEntryAddRoute
import de.cgi.android.timeentry.TimeEntryEditRoute
import de.cgi.android.timeentry.TimeEntryListRoute
import de.cgi.common.data.model.Project
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.TimeEntry


class RouterImpl(
    private val navigationController: NavHostController
) : Router {
    override fun showSignIn() {
        navigationController.navigate(SignInScreenRoute.route) {
            popUpTo(0)
        }
    }

    override fun showSignUp() {
        navigationController.navigate(SignUpScreenRoute.route) {
            popUpTo(SignInScreenRoute.route) {
                inclusive = true
                saveState = false
            }
        }
    }

    override fun showTimeEntryList() {
        navigationController.navigate(TimeEntryListRoute.route) {
            popUpTo(SignInScreenRoute.route) {
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
        navigationController.navigate(ProjectListRoute.route){
            popUpTo(SignInScreenRoute.route) {
                inclusive = true
                saveState = false
            }
        }
    }

    override fun showProjectEdit(project: Project) {
        navigationController.navigate(
            ProjectEditRoute.buildAddEditProjectRoute(project.id))
    }

    override fun showProjectAdd() {
        navigationController.navigate(ProjectAddRoute.route)
    }

    override fun showDashboard() {
        navigationController.navigate(DashboardScreenRoute.route)
    }

    override fun showTeamDashboard() {
        navigationController.navigate(TeamDashboardScreenRoute.route)
    }

    override fun showTeamList() {
        navigationController.navigate(TeamListScreenRoute.route)
    }

    override fun showTeamEdit(team: Team) {
        navigationController.navigate(TeamEditRoute.buildEditTeamRoute(team.id))
    }

    override fun showTeamAdd() {
        navigationController.navigate(TeamAddRoute.route)
    }

    override fun showSettings() {
        navigationController.navigate(SettingsHomeRoute.route)
    }

    override fun showAccount() {
        navigationController.navigate(AccountScreenRoute.route)
    }

    override fun showCalender() {
        navigationController.navigate(CalenderScreenRoute.route)
    }
    override fun back() {
        navigationController.popBackStack()
    }

    override fun navigationUp(): Boolean {
        return navigationController.navigateUp()
    }

    override fun getCurrentRoute(): String? {
        return navigationController.currentBackStackEntry?.destination?.route
    }
}