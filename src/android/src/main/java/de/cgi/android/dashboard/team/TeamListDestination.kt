package de.cgi.android.dashboard.team

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.navigation.Router
import de.cgi.common.UserRepository
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TeamListDestination(
    router: Router,
) {
    val viewModel = getViewModel<TeamListViewModel>()
    val teamListState by viewModel.listState.collectAsState()

    TeamListScreen(
        teamListState = teamListState.teamListState,
        reloadTeams = viewModel::getTeams,
        onNewTeamClick = { router.showTeamAdd() },
        onTeamClick = { router.showTeamEdit(it) },
    )
}