package de.cgi.android.util


import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.format() : String {
    val day = if (this.dayOfMonth < 10) {
        "0${this.dayOfMonth}"
    } else this.dayOfMonth
    val month = if (this.monthNumber < 10) {
        "0${this.monthNumber}"
    } else this.monthNumber
    return "$day.$month.${this.year}"
}
