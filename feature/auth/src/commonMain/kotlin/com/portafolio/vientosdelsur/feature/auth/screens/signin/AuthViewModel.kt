package com.portafolio.vientosdelsur.feature.auth.screens.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.onError
import com.portafolio.vientosdelsur.domain.auth.AuthError
import com.portafolio.vientosdelsur.domain.auth.oauth.GoogleAuthError
import com.portafolio.vientosdelsur.domain.auth.oauth.GoogleAuthService
import com.portafolio.vientosdelsur.domain.auth.signin.SignInUseCase
import com.portafolio.vientosdelsur.domain.auth.signup.SignUpUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import vientosdelsur.feature.auth.generated.resources.*
import vientosdelsur.feature.auth.generated.resources.Res
import vientosdelsur.feature.auth.generated.resources.credential_mismatch_error
import vientosdelsur.feature.auth.generated.resources.invalid_email_error

internal class AuthViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val googleAuthService: GoogleAuthService
) : ViewModel() {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set
    var authType by mutableStateOf(AuthScreenType.SIGN_IN)
        private set

    private val _validationErrors = MutableStateFlow(AuthValidationErrors())
    val validationErrors = _validationErrors.asStateFlow()

    private val _channel = Channel<String>()
    val channel = _channel.receiveAsFlow()

    fun onEmailChanged(text: String) {
        email = text
        _validationErrors.update { it.copy(emailError = null) }
    }

    fun onPasswordChanged(text: String) {
        password = text
        _validationErrors.update { it.copy(passwordError = null) }
    }

    fun onConfirmPasswordChanged(text: String) {
        confirmPassword = text
        _validationErrors.update { it.copy(passwordError = null) }
    }

    fun onAuthTypeChanged() {
        email = ""
        password = ""
        confirmPassword = ""

        authType = when (authType) {
            AuthScreenType.SIGN_IN -> AuthScreenType.SIGN_UP
            AuthScreenType.SIGN_UP -> AuthScreenType.SIGN_IN
        }
        _validationErrors.update { AuthValidationErrors() }
    }

    fun onSignIn() {
        viewModelScope.launch {
            signInUseCase(email, password)
                .onError { authError ->
                    when (authError) {
                        AuthError.INVALID_EMAIL -> _validationErrors.update { it.copy(emailError = Res.string.invalid_email_error) }
                        AuthError.PASSWORD_MISMATCH -> _validationErrors.update { it.copy(passwordError = Res.string.credential_mismatch_error) }
                        AuthError.REMOTE -> _channel.send(getString(Res.string.remote_error))
                    }
                }
        }
    }

    fun onSignUp() {
        viewModelScope.launch {
            signUpUseCase(email, password, confirmPassword)
                .onError { authError ->
                    when (authError) {
                        AuthError.INVALID_EMAIL -> _validationErrors.update { it.copy(emailError = Res.string.invalid_email_error) }
                        AuthError.PASSWORD_MISMATCH -> _validationErrors.update { it.copy(passwordError = Res.string.password_mismatch_error) }
                        AuthError.REMOTE -> _channel.send(getString(Res.string.remote_error))
                    }
                }
        }
    }

    fun onSignInWithGoogle() {
        viewModelScope.launch {
            googleAuthService.login()
                .onError { googleAuthError ->
                    when (googleAuthError) {
                        GoogleAuthError.NO_ACCOUNT_ON_DEVICE -> _channel.send(getString(Res.string.no_google_accounts_error))
                        GoogleAuthError.REMOTE -> _channel.send(getString(Res.string.remote_error))
                    }
                }
        }
    }
}

data class AuthValidationErrors(
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null
)