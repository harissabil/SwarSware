package com.harissabil.swarsware.common.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.toLocalDateTime

fun Instant.toHhMmSs(): String {
    val tz = TimeZone.currentSystemDefault()
    val localDateTime = this.toLocalDateTime(tz)
    val time = localDateTime.time

    return time.format(LocalTime.Format {
        hour()
        chars(":")
        minute()
        chars(":")
        second()
    })
}

fun Instant.toDdMmYyyyHhMmSs(): String {
    val tz = TimeZone.currentSystemDefault()
    val localDateTime = this.toLocalDateTime(tz)
    val date = localDateTime.date
    val time = localDateTime.time

    return date.format(LocalDate.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        chars(" ")
        dayOfMonth()
        chars(", ")
        year()
    }) + " @ " + time.format(LocalTime.Format {
        hour()
        chars(":")
        minute()
        chars(":")
        second()
    })
}