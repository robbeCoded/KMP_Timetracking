package de.cgi.android.projects

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import compose.icons.feathericons.*
import de.cgi.android.timeentry.TimeEntryListViewModel
import de.cgi.android.ui.components.*
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectsScreen(
    viewModel: TimeEntryListViewModel = getViewModel<TimeEntryListViewModel>(),
    navController: NavController
) {

}

