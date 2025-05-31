@file:OptIn(ExperimentalComposeUiApi::class)

package com.portafolio.vientosdelsur.feature.auth.screens.signup

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.feature.auth.screens.signup.components.ProgressScaffold
import com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation.RegistrationNavHost
import com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation.RegistrationRoute
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun RegistrationFlowScreenRoot(
    modifier: Modifier = Modifier,
    registrationFlowViewModel: RegistrationFlowViewModel
) {
    val user by registrationFlowViewModel.employee.collectAsStateWithLifecycle()
    RegistrationFlowScreen(
        modifier = modifier,
        user = user,
        progress = registrationFlowViewModel.progress,
        onNavigationEvent = registrationFlowViewModel::onNavigationEvent
    )
}

@Composable
private fun RegistrationFlowScreen(
    modifier: Modifier = Modifier,
    progress: Float,
    user: Employee?,
    onNavigationEvent: (RegistrationRoute) -> Unit
) {
    val progressState = animateFloatAsState(progress)
    val navController = rememberNavController()

    ProgressScaffold(
        modifier = modifier,
        onGoBack = {},
        onSkipStep = null,
        progress = progressState::value,
        content = {
            RegistrationNavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                user = user,
                onNavigationEvent = onNavigationEvent
            )
        }
    )
}

@Preview
@Composable
private fun RegistrationFlowScreenPreview() {
    VientosDelSurTheme {
        Surface {
            RegistrationFlowScreen(
                user = null,
                progress = 0.1f,
                onNavigationEvent = {}
            )
        }
    }
}