package com.portafolio.vientosdelsur.room.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRole
import com.portafolio.vientosdelsur.room.screens.foryou.housekeeper.RoomScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object ForYouHousekeeper

@Serializable
data object ForYouSupervisor

fun NavController.navigateToHousekeeperForYou(navOptions: NavOptions) = navigate(route = ForYouHousekeeper, navOptions)
fun NavController.navigateToSupervisorForYou(navOptions: NavOptions) = navigate(route = ForYouSupervisor, navOptions)

fun NavGraphBuilder.housekeeperForYou() {
    val employee = Employee(
        id = 1,
        firstName = "Flor",
        lastName = "Muñoz",
        role = EmployeeRole.HOUSEKEEPER
    )
    composable<ForYouHousekeeper> {
        RoomScreenRoot(employee = employee)
    }
}

fun NavGraphBuilder.supervisorForYou() {
    val employee = Employee(
        id = 1,
        firstName = "Luisa",
        lastName = "Super",
        role = EmployeeRole.SUPERVISOR
    )
    composable<ForYouSupervisor> {
        RoomScreenRoot(employee = employee)
    }
}