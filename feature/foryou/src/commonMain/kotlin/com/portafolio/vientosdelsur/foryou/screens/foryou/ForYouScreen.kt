package com.portafolio.vientosdelsur.foryou.screens.foryou

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.portafolio.vientosdelsur.domain.employee.EmployeeRole
import com.portafolio.vientosdelsur.foryou.screens.foryou.housekeeper.HousekeeperForYouScreenRoot
import com.portafolio.vientosdelsur.foryou.screens.foryou.supervisor.SupervisorForYouScreenRoot
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun ForYouScreenRoot(modifier: Modifier = Modifier, forYouViewModel: ForYouViewModel = koinViewModel()) {
    val employee by forYouViewModel.user.collectAsStateWithLifecycle()

    when(employee?.role) {
        EmployeeRole.HOUSEKEEPER -> HousekeeperForYouScreenRoot(
            modifier = modifier,
            employee = employee!!
        )
        EmployeeRole.SUPERVISOR -> SupervisorForYouScreenRoot(
            modifier = modifier,
            employee = employee!!
        )
        EmployeeRole.ADMIN -> {}
        null -> {}
    }
}

