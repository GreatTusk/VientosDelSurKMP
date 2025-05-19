package com.portafolio.vientosdelsur.foryou.screens.foryou

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.employee.EmployeeRole
import com.portafolio.vientosdelsur.foryou.screens.foryou.housekeeper.HousekeeperForYouScreenRoot
import com.portafolio.vientosdelsur.foryou.screens.foryou.supervisor.SupervisorForYouScreenRoot
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun ForYouScreenRoot(modifier: Modifier = Modifier, forYouViewModel: ForYouViewModel = koinViewModel()) {
    val employeeState by forYouViewModel.user.collectAsStateWithLifecycle()

    when (val employee = employeeState) {
        is Result.Success -> {
            when (employee.data.role) {
                EmployeeRole.HOUSEKEEPER -> HousekeeperForYouScreenRoot(
                    modifier = modifier,
                    employee = employee.data
                )

                EmployeeRole.SUPERVISOR -> SupervisorForYouScreenRoot(
                    modifier = modifier,
                    employee = employee.data
                )

                EmployeeRole.ADMIN -> {

                }
            }
        }

        else -> {}
    }
}

