package com.portafolio.vientosdelsur.feature.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.feature.auth.screens.components.AuthSurface
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import vientosdelsur.feature.auth.generated.resources.Res
import vientosdelsur.feature.auth.generated.resources.vientos_del_sur_logo

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
        onSignInGoogle = viewModel::onSignInWithGoogle,
        onSignIn = viewModel::onSignIn,
        authType = viewModel.authType,
        onAuthTypeChanged = viewModel::onAuthTypeChanged
    )
}

@Composable
internal fun AuthScreen(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    confirmPassword: String,
    authType: AuthScreenType,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    onSignInGoogle: () -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onAuthTypeChanged: () -> Unit
) {
    val windowInfo = currentWindowAdaptiveInfo()
    val isExpanded = windowInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED

    Row(modifier = modifier) {
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
            onSignIn = onSignIn,
            authType = authType,
            onAuthTypeChanged = onAuthTypeChanged
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
private fun AuthScreenDetail(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    confirmPassword: String,
    authType: AuthScreenType,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    onSignInGoogle: () -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onAuthTypeChanged: () -> Unit
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
                onSignIn = onSignIn,
                onForgotPassword = {},
                authType = authType,
                onAuthTypeChanged = onAuthTypeChanged
            )
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
                onConfirmPasswordChanged = {},
                authType = AuthScreenType.SIGN_UP,
                onAuthTypeChanged = {}
            )
        }
    }
}