package com.portafolio.vientosdelsur.feature.shift.admin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ShiftAdminRoute

fun NavController.navigateToShiftAdmin(navOptions: NavOptions? = null) = navigate(route = ShiftAdminRoute, navOptions)

fun NavGraphBuilder.shiftAdminGraph() {
    composable<ShiftAdminRoute> {
    }
}