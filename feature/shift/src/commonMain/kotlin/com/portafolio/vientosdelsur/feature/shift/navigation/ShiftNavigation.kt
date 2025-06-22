package com.portafolio.vientosdelsur.feature.shift.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.portafolio.vientosdelsur.feature.shift.screens.employee.OccupationShiftScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object ShiftRoute

fun NavController.navigateToShift(navOptions: NavOptions? = null) = navigate(route = ShiftRoute, navOptions)

fun NavGraphBuilder.shiftGraph() {
    composable<ShiftRoute> {
        OccupationShiftScreenRoot()
    }
}