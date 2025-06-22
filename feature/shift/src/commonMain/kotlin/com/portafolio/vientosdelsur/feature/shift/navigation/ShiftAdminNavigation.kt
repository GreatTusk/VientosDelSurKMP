package com.portafolio.vientosdelsur.feature.shift.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.portafolio.vientosdelsur.feature.shift.screens.admin.ShiftReviewerScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object ShiftAdminRoute

fun NavController.navigateToShiftAdmin(navOptions: NavOptions? = null) = navigate(route = ShiftAdminRoute, navOptions)

fun NavGraphBuilder.shiftAdminGraph() {
    composable<ShiftAdminRoute> {
        ShiftReviewerScreenRoot()
    }
}