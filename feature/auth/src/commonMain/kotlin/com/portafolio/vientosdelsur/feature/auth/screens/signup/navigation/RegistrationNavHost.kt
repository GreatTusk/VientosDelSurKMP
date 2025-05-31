package com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.ProfileStep
import kotlinx.serialization.Serializable

@Serializable
data object Profile

@Serializable
data object Occupation

@Composable
internal fun RegistrationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    user: Employee?,
    onContinue: () -> Unit
) {
    NavHost(modifier = modifier, navController = navController, startDestination = Profile) {
        composable<Profile> {
            ProfileStep(
                onContinue = { onContinue(); navController.navigate(Occupation) },
                initialData = user
            )
        }

        composable<Occupation> {
            Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.error))
        }
    }
}