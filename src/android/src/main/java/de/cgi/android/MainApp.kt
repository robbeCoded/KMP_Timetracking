package de.cgi.android

import TimetrackingTheme
import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import de.cgi.android.di.appModule
import de.cgi.android.navigation.MainNavigation
import org.kodein.di.compose.withDI

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun MainApp(appState: AppState, context: Context) = withDI(appModule(context))
 {
    TimetrackingTheme {
        MainNavigation(appState)
    }
}