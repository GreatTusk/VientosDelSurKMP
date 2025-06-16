package com.portafolio.vientosdelsur.feature.hotel.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis.RoomAnalysisScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object Hotel

fun NavHostController.navigateToHotel(navOptions: NavOptions) {
    navigate(Hotel, navOptions = navOptions)
}

fun NavGraphBuilder.hotelGraph() {
    composable<Hotel> {
        RoomAnalysisScreenRoot()
    }
}