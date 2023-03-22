package de.cgi.android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import de.cgi.android.AuthScreen
import de.cgi.android.viewmodel.AuthViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun EntryPoint() {
    val navController = rememberNavController()

    // Start Koin and get your AuthViewModel instance
    val viewModel = getViewModel<AuthViewModel>()
    // Call your AuthScreen composable with the AuthViewModel
    AuthScreen(navController,viewModel)
}
