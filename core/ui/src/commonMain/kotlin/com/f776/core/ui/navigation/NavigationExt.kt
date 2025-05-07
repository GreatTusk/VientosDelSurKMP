package com.f776.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import org.koin.compose.viewmodel.koinViewModel
import kotlin.reflect.KClass

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}

// No public method to check in a loop for a route
// Should expose a base Route interface...
fun <T> isTopLevelRoute(currentDestination: NavDestination?, topLevelRoutes: List<T>): Boolean {
    if (currentDestination == null) {
        return true
    }

    return topLevelRoutes.any { route ->
        currentDestination.hasRoute(route!!::class)
    }
}

fun <T> isTabSelected(
    currentDestination: NavDestination?,
    topLevelRoute: T,
): Boolean {
    if (currentDestination == null) {
        return true
    }
    return currentDestination.hierarchy.any { it.hasRoute(topLevelRoute!!::class) }
}

fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false
