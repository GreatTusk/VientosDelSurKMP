package com.portafolio.vientosdelsur.foryou.screens.foryou

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.foryou.screens.foryou.housekeeper.HousekeeperForYouScreenRoot
import com.portafolio.vientosdelsur.foryou.screens.foryou.supervisor.SupervisorForYouScreenRoot
import io.ktor.http.*
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
internal fun ForYouScreenRoot(
    modifier: Modifier = Modifier,
    forYouViewModel: ForYouViewModel = koinViewModel()
) {
    val employeeState by forYouViewModel.user.collectAsStateWithLifecycle()

    when (val employee = employeeState) {
        is Result.Success -> {
            when (employee.data.occupation) {
                Occupation.HOUSEKEEPER -> HousekeeperForYouScreenRoot(
                    modifier = modifier,
                    housekeeperForYouViewModel = koinViewModel { parametersOf(employee.data.id) },
                    employee = employee.data
                )

                Occupation.SUPERVISOR -> SupervisorForYouScreenRoot(
                    modifier = modifier,
                    employee = employee.data
                )

                Occupation.ADMIN -> {}
            }
        }

        else -> {}
    }
}

