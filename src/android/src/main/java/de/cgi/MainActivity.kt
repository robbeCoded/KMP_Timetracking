package de.cgi

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import de.cgi.android.MainApp
import de.cgi.android.rememberAppState
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class, ExperimentalFoundationApi::class
)
class MainActivity : ComponentActivity(), DIAware {

    override val di by closestDI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            val appState = rememberAppState()
            MainApp(
                appState = appState,
                context = this
            )
        }
    }
}

