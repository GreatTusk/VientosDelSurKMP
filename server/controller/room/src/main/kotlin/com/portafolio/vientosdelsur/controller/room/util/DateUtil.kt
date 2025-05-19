package com.portafolio.vientosdelsur.controller.room.util

import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun RoutingCall.parseDateFromQueryParams(): LocalDate {
    return request.queryParameters["date"]?.let {
        LocalDate.parse(it)
    } ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}