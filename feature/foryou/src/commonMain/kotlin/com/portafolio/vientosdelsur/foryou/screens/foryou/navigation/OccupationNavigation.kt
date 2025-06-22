package com.portafolio.vientosdelsur.foryou.screens.foryou.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.foryou.screens.foryou.ForYouViewModel
import com.portafolio.vientosdelsur.foryou.screens.foryou.admin.AdminForYouScreenRoot
import com.portafolio.vientosdelsur.foryou.screens.foryou.housekeeper.HousekeeperForYouScreenRoot
import com.portafolio.vientosdelsur.foryou.screens.foryou.supervisor.SupervisorForYouScreenRoot
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun OccupationNavigation(
    modifier: Modifier = Modifier,
    onNavigateToImageAnalysis: () -> Unit
) {
    val forYouViewModel = koinViewModel<ForYouViewModel>()
    val employee by forYouViewModel.employee.collectAsStateWithLifecycle()

    when (employee) {
        null -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        else -> when (employee!!.occupation) {
            Occupation.HOUSEKEEPER, Occupation.COOK -> HousekeeperForYouScreenRoot(
                modifier = modifier,
                employee = employee!!,
                onNavigateToImageAnalysis = onNavigateToImageAnalysis
            )

            Occupation.SUPERVISOR -> SupervisorForYouScreenRoot(
                modifier = modifier,
                employee = employee!!
            )

            Occupation.ADMIN -> AdminForYouScreenRoot(modifier = modifier, employee = employee!!)
        }
    }
}