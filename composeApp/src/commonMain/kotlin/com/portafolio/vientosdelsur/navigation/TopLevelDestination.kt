package com.portafolio.vientosdelsur.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.portafolio.vientosdelsur.feature.hotel.navigation.Hotel
import com.portafolio.vientosdelsur.feature.hotel.navigation.navigateToHotel
import com.portafolio.vientosdelsur.feature.shift.navigation.ShiftRoute
import com.portafolio.vientosdelsur.feature.shift.navigation.navigateToShift
import com.portafolio.vientosdelsur.foryou.navigation.ForYou
import com.portafolio.vientosdelsur.foryou.navigation.navigateToForYou
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import vientosdelsur.composeapp.generated.resources.Res
import vientosdelsur.composeapp.generated.resources.app_name
import vientosdelsur.composeapp.generated.resources.for_you_title
import vientosdelsur.composeapp.generated.resources.rooms_title
import vientosdelsur.composeapp.generated.resources.shift_title
import kotlin.reflect.KClass

@Serializable
data object TopLevelNavigation

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: StringResource,
    val iconText: StringResource,
    val route: KClass<*>,
) {
    FOR_YOU(
        selectedIcon = Icons.Default.Face,
        unselectedIcon = Icons.Outlined.Face,
        title = Res.string.app_name,
        iconText = Res.string.for_you_title,
        route = ForYou::class
    ),
    HOTEL(
        selectedIcon = Icons.Default.Bed,
        unselectedIcon = Icons.Outlined.Bed,
        title = Res.string.app_name,
        iconText = Res.string.rooms_title,
        route = Hotel::class
    ),
    SHIFT(
        selectedIcon = Icons.Default.Schedule,
        unselectedIcon = Icons.Outlined.Schedule,
        title = Res.string.app_name,
        iconText = Res.string.shift_title,
        route = ShiftRoute::class
    );

    fun navigateToTopLevelDestination(navController: NavHostController) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (this) {
            FOR_YOU -> navController.navigateToForYou(topLevelNavOptions)
            HOTEL -> navController.navigateToHotel(topLevelNavOptions)
            SHIFT -> navController.navigateToShift(topLevelNavOptions)
        }
    }
}