package de.cgi.pages.calender

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import de.cgi.components.layouts.PageLayout
import org.jetbrains.compose.web.dom.Text


@Page(routeOverride = "home")
@Composable
fun CalenderScreen() {
    val ctx = rememberPageContext()
    PageLayout(title = "Calender", pageContext = ctx) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Nothing here yet. Check out the other Screens.")
        }
    }
}