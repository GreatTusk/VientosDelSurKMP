@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.portafolio.vientosdelsur.feature.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirectiveWithTwoPanesOnMediumWidth
import androidx.compose.material3.adaptive.layout.calculateThreePaneScaffoldValue
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.feature.auth.screens.components.AuthSurface
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import vientosdelsur.feature.auth.generated.resources.*

@Composable
internal fun AuthScreenRoot(modifier: Modifier = Modifier, onNavigateToHome: () -> Unit) {
    val viewModel = koinViewModel<AuthViewModel>()

    AuthScreen(
        modifier = modifier,
        onSignUp = viewModel::onSignUp,
        email = viewModel.email,
        password = viewModel.password,
        confirmPassword = viewModel.confirmPassword,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onConfirmPasswordChanged = viewModel::onConfirmPasswordChanged,
        onSignInGoogle = viewModel::signInWithGoogle,
        onSignIn = {}
    )
}

@Composable
private fun AuthScreenDetail(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    confirmPassword: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    onSignInGoogle: () -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
) {
    Surface(modifier = modifier.statusBarsPadding()) {
        Column {
            Image(
                modifier = Modifier.width(width = 200.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(Res.drawable.vientos_del_sur_logo),
                contentDescription = "Logo"
            )
            AuthSurface(
                modifier = Modifier.fillMaxHeight(),
                onSignUp = onSignUp,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
                onConfirmPasswordChanged = onConfirmPasswordChanged,
                onSignInGoogle = onSignInGoogle,
                onSignIn = onSignIn
            )
        }
    }
}

@Composable
internal fun AuthScreen(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    confirmPassword: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    onSignInGoogle: () -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
) {
    val windowInfo = currentWindowAdaptiveInfo()
    val isExpanded = windowInfo.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED

    Row {
        AuthScreenDetail(
            modifier = if (isExpanded) Modifier.weight(1f).padding(horizontal = 64.dp) else Modifier,
            onSignUp = onSignUp,
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            onEmailChanged = onEmailChanged,
            onPasswordChanged = onPasswordChanged,
            onConfirmPasswordChanged = onConfirmPasswordChanged,
            onSignInGoogle = onSignInGoogle,
            onSignIn = onSignIn
        )
        if (isExpanded) {
            Box(modifier = Modifier.weight(1f).fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.width(width = 200.dp)
                        .padding(vertical = 16.dp),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(Res.drawable.vientos_del_sur_logo),
                    contentDescription = "Logo"
                )
            }
        }
    }
}

@Composable
@Preview
private fun SignUpScreenPreview(modifier: Modifier = Modifier) {
    VientosDelSurTheme {
        Surface {
            AuthScreenDetail(
                onSignUp = {},
                email = "",
                password = "",
                confirmPassword = "",
                onEmailChanged = {},
                onPasswordChanged = {},
                onSignIn = {},
                onSignInGoogle = {},
                onConfirmPasswordChanged = {}
            )
        }
    }
}