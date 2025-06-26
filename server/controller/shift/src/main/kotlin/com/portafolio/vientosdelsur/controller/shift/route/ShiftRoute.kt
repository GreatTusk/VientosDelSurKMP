package com.portafolio.vientosdelsur.controller.shift.route

import com.f776.core.common.onEmpty
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.core.controller.util.parseDateFromQueryParams
import com.portafolio.vientosdelsur.core.controller.util.today
import com.portafolio.vientosdelsur.service.shift.ShiftSchedulerService
import com.portafolio.vientosdelsur.service.shift.ShiftService
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import org.koin.ktor.ext.inject

fun Application.shiftRoute() {
    val shiftSchedulerService by inject<ShiftSchedulerService>()
    val shiftService by inject<ShiftService>()

    routing {
        swaggerUI("swagger/shift", swaggerFile = "openapi/documentation-shift.yaml")
        route("/shifts") {
            get("/employees-working") {
                val date = try {
                    call.parseDateFromQueryParams() ?: today()
                } catch (e: IllegalArgumentException) {
                    return@get call.respond(HttpStatusCode.BadRequest, "Invalid date format")
                }

                shiftService.getEmployeesWorkingOn(date)
                    .onSuccess {
                        call.respond(it)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError, "Error retrieving employees: $it")
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound, "No employees on shift")
                    }
            }

            get("/employee/{id}") {
                val employeeId =
                    call.pathParameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)

                shiftService.getMonthlyShiftsFor(employeeId)
                    .onSuccess {
                        call.respond(it)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError, "Error retrieving shifts: $it")
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound, "No shifts found")
                    }
            }

            route("/scheduling") {

                get {
                    val date = try {
                        call.parseDateFromQueryParams() ?: today()
                    } catch (e: IllegalArgumentException) {
                        return@get call.respond(HttpStatusCode.BadRequest, "Invalid date format")
                    }

                    shiftService.getMonthlyShifts(date)
                        .onSuccess {
                            call.respond(it)
                        }
                        .onError {
                            call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                        }
                }

                post("/generate") {
                    val date = try {
                        call.parseDateFromQueryParams() ?: nextMonth()
                    } catch (e: IllegalArgumentException) {
                        return@post call.respond(HttpStatusCode.BadRequest, "Invalid date format")
                    }

                    shiftSchedulerService.generateDraftSchedule(date)
                        .onSuccess {
                            call.respond(it)
                        }
                        .onError {
                            call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                        }
                }

                post("/publish") {
                    val draft = try {
                        call.receive<List<MonthlyShiftDistributionDto>>()
                    } catch (_: ContentTransformationException) {
                        return@post call.respond(HttpStatusCode.BadRequest, "Malformed body")
                    }

                    shiftSchedulerService.publishSchedule(draft)
                        .onSuccess {
                            call.respond(HttpStatusCode.OK, "Saved draft successfully")
                        }
                        .onError {
                            call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                        }
                }
            }
        }
    }
}

private fun nextMonth() = today()
    .toJavaLocalDate()
    .withDayOfMonth(15)
    .plusMonths(1)
    .toKotlinLocalDate()