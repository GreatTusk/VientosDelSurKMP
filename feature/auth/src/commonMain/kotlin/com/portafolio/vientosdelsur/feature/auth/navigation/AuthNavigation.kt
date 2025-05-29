package com.portafolio.vientosdelsur.feature.auth.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.portafolio.vientosdelsur.feature.auth.screens.signin.AuthScreenRoot
import com.portafolio.vientosdelsur.feature.auth.screens.signup.RegistrationFlowScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object AuthNavigation

@Serializable
data object Auth

@Serializable
data object Registration

fun NavGraphBuilder.authGraph(onSignIn: () -> Unit) {
    navigation<AuthNavigation>(startDestination = Auth) {
        composable<Auth> {
            AuthScreenRoot(modifier = Modifier.fillMaxSize(), onNavigateToHome = onSignIn)
        }

        composable<Registration> {
            RegistrationFlowScreenRoot()
        }
    }
}

fun NavHostController.navigateToRegistration() {
    navigate(Registration)
}