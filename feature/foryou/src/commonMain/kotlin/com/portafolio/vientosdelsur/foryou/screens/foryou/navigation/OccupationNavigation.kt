package com.portafolio.vientosdelsur.foryou.screens.foryou.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.f776.core.ui.components.ObserveAsEvents
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.foryou.screens.foryou.ForYouEvent
import com.portafolio.vientosdelsur.foryou.screens.foryou.ForYouViewModel
import com.portafolio.vientosdelsur.foryou.screens.foryou.housekeeper.HousekeeperForYouScreenRoot
import com.portafolio.vientosdelsur.foryou.screens.foryou.supervisor.SupervisorForYouScreenRoot
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun OccupationNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onNavigateToImageAnalysis: () -> Unit
) {
    val forYouViewModel = koinViewModel<ForYouViewModel>()
    val employee by forYouViewModel.employee.collectAsStateWithLifecycle()

    ObserveAsEvents(forYouViewModel.navChannel) {
        when (it) {
            is ForYouEvent.Navigation -> when (it.occupation) {
                Occupation.HOUSEKEEPER -> navController.navigate(OccupationRoute.Housekeeper)
                Occupation.SUPERVISOR -> navController.navigate(OccupationRoute.Supervisor)
                Occupation.ADMIN -> navController.navigate(OccupationRoute.Admin)
                null -> navController.navigate(OccupationRoute.Loading)
            }
        }
    }

    NavHost(modifier = modifier, navController = navController, startDestination = ForYouNavigation) {
        navigation<ForYouNavigation>(startDestination = OccupationRoute.Loading) {
            composable<OccupationRoute.Loading> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            composable<OccupationRoute.Housekeeper> {
                HousekeeperForYouScreenRoot(
                    housekeeperForYouViewModel = koinViewModel { parametersOf(employee!!.id) },
                    employee = employee!!,
                    onNavigateToImageAnalysis = onNavigateToImageAnalysis
                )
            }
            composable<OccupationRoute.Supervisor> {
                SupervisorForYouScreenRoot(
                    employee = employee!!
                )
            }
            composable<OccupationRoute.Admin> {
            }
        }
    }

}