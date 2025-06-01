package com.portafolio.vientosdelsur.feature.auth.screens.signup

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.portafolio.vientosdelsur.feature.auth.screens.signup.components.ProgressScaffold
import com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation.RegistrationNavHost

@Composable
internal fun RegistrationFlowScreenRoot(
    modifier: Modifier = Modifier,
    registrationFlowViewModel: RegistrationFlowViewModel
) {
    val user by registrationFlowViewModel.employee.collectAsStateWithLifecycle()
    val progressState = animateFloatAsState(registrationFlowViewModel.progress)
    val navController = rememberNavController()

    ProgressScaffold(
        modifier = modifier,
        onGoBack = navController::navigateUp,
        onSkipStep = null,
        progress = progressState::value,
        content = {
            RegistrationNavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                user = user,
                onNavigationEvent = registrationFlowViewModel::onNavigationEvent,
                occupationOption = registrationFlowViewModel.occupation,
                onOccupationSelected = registrationFlowViewModel::onOccupationSelected,
                formattedDate = registrationFlowViewModel.hireDate?.formatted,
                onDateSelected = registrationFlowViewModel::onHireDateSelected,
                dayOff = registrationFlowViewModel.dayOff,
                onDayOffSelected = registrationFlowViewModel::onDayOffSelected
            )
        }
    )
}