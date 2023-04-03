import androidx.compose.runtime.*
import de.cgi.common.api.setBaseUrl
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.di.initKoin
import de.cgi.common.repository.TimeEntryRepository
import kotlinx.coroutines.InternalCoroutinesApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

private val koin = initKoin(enableNetworkLogs = true).koin

@InternalCoroutinesApi
fun main() {
    val repo = koin.get<TimeEntryRepository>()
    setBaseUrl("http://localhost:8080")

    renderComposable(rootElementId = "root") {
        Style(TextStyles)

        var timeEntries by remember { mutableStateOf(emptyList<TimeEntry>()) }

        LaunchedEffect(true) {
            timeEntries = repo.getTimeEntries(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ1c2VycyIsImlzcyI6Imh0dHA6Ly8wLjAuMC4wOjgwODAiLCJleHAiOjE3MTA1MDgyODksInVzZXJJZCI6IjY0MTMxNTdhYTZhOWNlNmU5YzUwNmNlYSJ9.IeyhklpFjNm5-TDWDTCJTJ63oa3wEXi6G6zYCUmzy2U", true)
        }

        Div(attrs = { style { padding(16.px) } }) {
            H1(attrs = { classes(TextStyles.titleText) }) {
                Text("Timetracking")
            }

            timeEntries.forEach { entry ->
                Div(
                    attrs = {
                        style {
                            display(DisplayStyle.Flex)
                            alignItems(AlignItems.Center)
                        }
                    }
                ) {
                    Span(attrs = { classes(TextStyles.itemText) }) {
                        Text("${entry.description}: ${entry.startTime} - ${entry.endTime}, Project: ${entry.projectId}")
                    }
                }
            }
        }
    }
}

object TextStyles : StyleSheet() {

    val titleText by style {
        color(rgb(23,24, 28))
        fontSize(50.px)
        property("font-size", 50.px)
        property("letter-spacing", (-1.5).px)
        property("font-weight", 900)
        property("line-height", 58.px)

        property(
            "font-family",
            "Gotham SSm A,Gotham SSm B,system-ui,-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Oxygen,Ubuntu,Cantarell,Droid Sans,Helvetica Neue,Arial,sans-serif"
        )
    }

    val itemText by style {
        color(rgb(23,24, 28))
        fontSize(24.px)
        property("font-size", 28.px)
        property("letter-spacing", "normal")
        property("font-weight", 300)
        property("line-height", 40.px)

        property(
            "font-family",
            "Gotham SSm A,Gotham SSm B,system-ui,-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Oxygen,Ubuntu,Cantarell,Droid Sans,Helvetica Neue,Arial,sans-serif"
        )
    }
}
