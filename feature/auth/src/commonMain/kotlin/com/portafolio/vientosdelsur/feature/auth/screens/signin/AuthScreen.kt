package com.portafolio.vientosdelsur.feature.auth.screens.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.f776.core.ui.components.ObserveAsEvents
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.feature.auth.screens.signin.components.AuthSurface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import vientosdelsur.feature.auth.generated.resources.Res
import vientosdelsur.feature.auth.generated.resources.vientos_del_sur_logo

@Composable
internal fun AuthScreenRoot(modifier: Modifier = Modifier, onNavigateToHome: () -> Unit) {
    val viewModel = koinViewModel<AuthViewModel>()
    val validationErrors by viewModel.validationErrors.collectAsStateWithLifecycle()


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
        onAuthTypeChanged = viewModel::onAuthTypeChanged,
        validationErrors = validationErrors,
        errorFlow = viewModel.channel
    )
}

@Composable
private fun AuthScreen(
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
    onAuthTypeChanged: () -> Unit,
    validationErrors: AuthValidationErrors,
    errorFlow: Flow<String>
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
            onAuthTypeChanged = onAuthTypeChanged,
            validationErrors = validationErrors,
            errorFlow = errorFlow
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
    onAuthTypeChanged: () -> Unit,
    validationErrors: AuthValidationErrors,
    errorFlow: Flow<String>
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(events = errorFlow) {
        scope.launch {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
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
                onAuthTypeChanged = onAuthTypeChanged,
                validationErrors = validationErrors
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
                onAuthTypeChanged = {},
                validationErrors = AuthValidationErrors(),
                errorFlow = emptyFlow()
            )
        }
    }
}