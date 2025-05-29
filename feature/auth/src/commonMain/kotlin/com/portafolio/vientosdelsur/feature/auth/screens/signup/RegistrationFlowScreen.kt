package com.portafolio.vientosdelsur.feature.auth.screens.signup

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.feature.auth.screens.signup.components.ProgressScaffold
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.ProfileStep
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun RegistrationFlowScreenRoot(
    modifier: Modifier = Modifier,
    registrationFlowViewModel: RegistrationFlowViewModel
) {
    val user by registrationFlowViewModel.employee.collectAsStateWithLifecycle()
    RegistrationFlowScreen(user = user)
}

@Composable
private fun RegistrationFlowScreen(modifier: Modifier = Modifier, user: Employee?) {
    ProgressScaffold(
        onGoBack = {},
        onSkipStep = null,
        progress = { 0.1f },
        content = {
            ProfileStep(
                paddingValues = it,
                initialData = user,
                onContinue = {}
            )
        }
    )
}

@Preview
@Composable
private fun RegistrationFlowScreenPreview() {
    VientosDelSurTheme {
        Surface {
            RegistrationFlowScreen(user = null)
        }
    }
}