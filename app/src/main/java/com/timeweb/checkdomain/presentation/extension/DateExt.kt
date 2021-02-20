package com.timeweb.checkdomain.presentation.extension

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDate.formatWithPattern(pattern: String, locale: Locale = Locale("ru")): String {
    val dtf = DateTimeFormatter.ofPattern(pattern, locale)
    return this.format(dtf)
}

fun LocalDateTime.formatWithPattern(pattern: String, locale: Locale = Locale("ru")): String {
    val dtf = DateTimeFormatter.ofPattern(pattern, locale)
    return this.format(dtf)
}