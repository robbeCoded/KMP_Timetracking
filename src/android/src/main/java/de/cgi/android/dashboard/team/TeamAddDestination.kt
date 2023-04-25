package de.cgi.android.dashboard.team

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.navigation.Router
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TeamAddDestination(
    router: Router,
) {
    val viewModel = getViewModel<TeamAddViewModel>()
    val userListState by viewModel.listState.collectAsState()

    TeamAddScreen(
        userListState = userListState.userListState,
        onReloadUserList = viewModel::getUsers,
        onSubmit = viewModel::newTeam
    )
}