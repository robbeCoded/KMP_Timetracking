package de.cgi

import TimetrackingTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import de.cgi.android.NavGraphs



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            TimetrackingTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
