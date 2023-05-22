package de.cgi.common.util


import kotlinx.datetime.LocalDate

fun LocalDate.format() : String {
    val day = if (this.dayOfMonth < 10) {
        "0${this.dayOfMonth}"
    } else this.dayOfMonth
    val month = if (this.monthNumber < 10) {
        "0${this.monthNumber}"
    } else this.monthNumber
    return "$day.$month.${this.year}"
}
