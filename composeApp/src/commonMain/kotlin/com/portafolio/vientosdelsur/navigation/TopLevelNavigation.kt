package com.portafolio.vientosdelsur.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.f776.core.ui.navigation.isRouteInHierarchy
import org.jetbrains.compose.resources.stringResource

@Composable
fun TopLevelNavigation(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val adaptiveInfo = currentWindowAdaptiveInfo()

    val isTopLevelDestination by remember {
        derivedStateOf {
            backStackEntry?.destination?.isRouteInHierarchy(
                TopLevelNavigation::class
            ) == true
        }
    }

    val navigationSuiteType = with(adaptiveInfo) {
        if (!isTopLevelDestination) {
            NavigationSuiteType.None
        } else if (
            windowPosture.isTabletop ||
            windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.COMPACT
        ) {
            NavigationSuiteType.NavigationRail
        } else if (
            windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM
        ) {
            NavigationSuiteType.NavigationRail
        } else if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteType.NavigationBar
        }
    }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            if (!isTopLevelDestination) return@NavigationSuiteScaffold
            TopLevelDestination.entries.forEach { destination ->
                val isSelected = backStackEntry?.destination?.isRouteInHierarchy(destination.route) == true
                item(
                    icon = {
                        if (isSelected) {
                            Icon(
                                imageVector = destination.selectedIcon,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = destination.unselectedIcon,
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text(stringResource(destination.iconText)) },
                    selected = isSelected,
                    onClick = { destination.navigateToTopLevelDestination(navController) }
                )
            }
        },
        layoutType = navigationSuiteType
    ) {
        NavigationGraph(navController = navController)
    }
}

