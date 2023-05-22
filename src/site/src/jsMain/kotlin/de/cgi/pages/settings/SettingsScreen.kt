package de.cgi.pages.settings

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import de.cgi.components.layouts.PageLayout
import org.jetbrains.compose.web.dom.Text


@Page(routeOverride = "home")
@Composable
fun SettingsScreen() {
    PageLayout(title = "Settings") {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Nothing here yet. Check out the other Screens.")
        }
    }
}