package de.cgi.android.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import de.cgi.android.navigation.Router


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SettingHomeDestination(
    router: Router
) {
    SettingsHomeScreen()
}