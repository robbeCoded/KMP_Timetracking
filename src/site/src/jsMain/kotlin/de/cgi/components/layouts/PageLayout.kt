package de.cgi.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.PageContext
import de.cgi.components.sections.NavMenu
import de.cgi.components.styles.Theme
import kotlinx.browser.document
import org.jetbrains.compose.web.css.percent

@Composable
fun PageLayout(title: String, pageContext: PageContext, content: @Composable () -> Unit) {
    LaunchedEffect(title) {
        document.title = title
    }

    Box(
        Modifier
            .fillMaxWidth()
            .minHeight(100.percent)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().textAlign(TextAlign.Center),
            verticalAlignment = Alignment.Top
        ) {
            NavMenu(onPage = title, pageContext = pageContext)
            Box(
                modifier = Modifier
                    .width(85.percent)
                    .fillMaxHeight()
                    .backgroundColor(Theme.Background.rgb)
            ) {
                content()
            }
        }
    }
}