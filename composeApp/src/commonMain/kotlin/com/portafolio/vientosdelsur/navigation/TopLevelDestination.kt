package com.portafolio.vientosdelsur.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Face
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.portafolio.vientosdelsur.foryou.navigation.ForYou
import com.portafolio.vientosdelsur.foryou.navigation.navigateToForYou
import com.portafolio.vientosdelsur.room.navigation.ForYouHousekeeper
import com.portafolio.vientosdelsur.room.navigation.ForYouSupervisor
import com.portafolio.vientosdelsur.room.navigation.navigateToHousekeeperForYou
import com.portafolio.vientosdelsur.room.navigation.navigateToSupervisorForYou
import org.jetbrains.compose.resources.StringResource
import vientosdelsur.composeapp.generated.resources.Res
import vientosdelsur.composeapp.generated.resources.app_name
import vientosdelsur.composeapp.generated.resources.for_you_title
import vientosdelsur.composeapp.generated.resources.rooms_title
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: StringResource,
    val iconText: StringResource,
    val route: KClass<*>,
) {
    FOR_YOU_HOUSEKEEPER(
        selectedIcon = Icons.Default.Face,
        unselectedIcon = Icons.Outlined.Face,
        title = Res.string.app_name,
        iconText = Res.string.for_you_title,
        route = ForYouHousekeeper::class
    ),
    FOR_YOU_SUPERVISOR(
        selectedIcon = Icons.Default.Face,
        unselectedIcon = Icons.Outlined.Face,
        title = Res.string.app_name,
        iconText = Res.string.for_you_title,
        route = ForYouSupervisor::class
    ),
    HOTEL(
        selectedIcon = Icons.Default.Bed,
        unselectedIcon = Icons.Outlined.Bed,
        title = Res.string.app_name,
        iconText = Res.string.rooms_title,
        route = ForYou::class
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
            FOR_YOU_HOUSEKEEPER -> navController.navigateToHousekeeperForYou(topLevelNavOptions)
            FOR_YOU_SUPERVISOR -> navController.navigateToSupervisorForYou(topLevelNavOptions)
            HOTEL -> navController.navigateToForYou(topLevelNavOptions)
        }
    }
}