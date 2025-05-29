package com.portafolio.vientosdelsur.feature.auth.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.portafolio.vientosdelsur.feature.auth.screens.signin.AuthScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object AuthNavigation

@Serializable
data object Auth

fun NavGraphBuilder.authGraph(onSignIn: () -> Unit) {
    navigation<AuthNavigation>(startDestination = Auth) {
        composable<Auth> {
            AuthScreenRoot(modifier = Modifier.fillMaxSize(), onNavigateToHome = onSignIn)
        }
    }
}