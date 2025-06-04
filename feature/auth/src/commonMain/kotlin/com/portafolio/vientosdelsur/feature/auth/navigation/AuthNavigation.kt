package com.portafolio.vientosdelsur.feature.auth.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.portafolio.vientosdelsur.feature.auth.screens.signin.AuthScreenRoot
import com.portafolio.vientosdelsur.feature.auth.screens.signup.RegistrationFlowScreenRoot
import com.portafolio.vientosdelsur.feature.auth.screens.signup.RegistrationFlowViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object AuthNavigation

@Serializable
data object Auth

@Serializable
data class Registration(val userId: String, val userName: String, val profilePictureUrl: String?)

fun NavGraphBuilder.authGraph(onSignIn: () -> Unit) {
    navigation<AuthNavigation>(startDestination = Auth) {
        composable<Auth> {
            AuthScreenRoot(modifier = Modifier.fillMaxSize(), onNavigateToHome = onSignIn)
        }

        composable<Registration> {
            val registrationFlowViewModel: RegistrationFlowViewModel = koinViewModel()
            RegistrationFlowScreenRoot(registrationFlowViewModel = registrationFlowViewModel)
        }
    }
}

fun NavHostController.navigateToRegistration(userId: String, profilePictureUrl: String?, userName: String) {
    navigate(
        Registration(
            userId = userId,
            userName = userName,
            profilePictureUrl = profilePictureUrl
        )
    ) {
    }
}