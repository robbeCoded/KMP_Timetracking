package de.cgi.android.account

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import de.cgi.android.navigation.Router

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun AccountHomeDestination(
    router: Router
) {
    AccountHomeScreen()
}