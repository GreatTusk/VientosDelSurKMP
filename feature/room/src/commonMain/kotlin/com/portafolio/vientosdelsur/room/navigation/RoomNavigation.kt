package com.portafolio.vientosdelsur.room.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.portafolio.vientosdelsur.room.screens.housekeeperForYou.RoomScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object Room

fun NavController.navigateToRoom(navOptions: NavOptions) = navigate(route = Room, navOptions)

fun NavGraphBuilder.roomGraph() {
    composable<Room> {
        RoomScreenRoot()
    }
}