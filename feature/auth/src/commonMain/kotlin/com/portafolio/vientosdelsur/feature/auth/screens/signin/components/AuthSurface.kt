package com.portafolio.vientosdelsur.feature.auth.screens.signin.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.feature.auth.screens.signin.AuthScreenType
import com.portafolio.vientosdelsur.feature.auth.screens.signin.AuthValidationErrors
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import vientosdelsur.feature.auth.generated.resources.*
import vientosdelsur.feature.auth.generated.resources.Res

@Composable
internal fun AuthSurface(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    authType: AuthScreenType,
    confirmPassword: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    onSignInGoogle: () -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onForgotPassword: () -> Unit,
    onAuthTypeChanged: () -> Unit,
    validationErrors: AuthValidationErrors
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val showRegister = remember(authType) {
        authType == AuthScreenType.SIGN_UP
    }

    val borderColor = MaterialTheme.colorScheme.outline
    val outlinedIconBorder = remember { BorderStroke(1.dp, borderColor) }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Column(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(if (showRegister) Res.string.sign_up else Res.string.sign_in),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChanged,
                label = { Text(stringResource(Res.string.email)) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = stringResource(Res.string.email_content_description)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                supportingText = if (validationErrors.emailError != null) {
                    {
                        Text(
                            text = stringResource(validationErrors.emailError),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                } else null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth().semantics {
                    contentType = ContentType.EmailAddress
                }
            )

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChanged,
                label = { Text(stringResource(Res.string.password)) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = stringResource(Res.string.password_content_description)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = stringResource(Res.string.toggle_password_visibility)
                        )
                    }
                },
                supportingText = if (validationErrors.passwordError != null) {
                    {
                        Text(
                            text = stringResource(validationErrors.passwordError),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                } else null,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = if (showRegister) ImeAction.Next else ImeAction.Done
                ),
                keyboardActions = if (showRegister) KeyboardActions.Default else KeyboardActions {
                    onSignIn()
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().semantics {
                    contentType = ContentType.Password
                }
            )

            AnimatedContent(targetState = showRegister, modifier = Modifier.align(Alignment.End)) { isRegisterMode ->
                if (isRegisterMode) {
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = onConfirmPasswordChanged,
                        label = { Text(stringResource(Res.string.confirm_password)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = stringResource(Res.string.confirm_password_content_description)
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = stringResource(Res.string.toggle_confirm_password_visibility)
                                )
                            }
                        },
                        supportingText = if (validationErrors.passwordError != null) {
                            {
                                Text(
                                    text = stringResource(validationErrors.passwordError),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        } else null,
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions { onSignUp() },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().semantics {
                            contentType = ContentType.Password
                        }
                    )
                } else {
                    TextButton(onClick = onForgotPassword) {
                        Text(text = stringResource(Res.string.forgot_password_question))
                    }
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = if (showRegister) onSignUp else onSignIn
            ) {
                Text(text = stringResource(if (showRegister) Res.string.sign_up else Res.string.sign_in))
            }

            Text(
                text = stringResource(if (showRegister) Res.string.or_sign_up_with else Res.string.or_sign_in_with),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            OAuthButtonRow(onSignInGoogle, outlinedIconBorder, showRegister)

            Spacer(modifier = Modifier.weight(1f))

            HorizontalDivider()

            Row(
                modifier = Modifier.navigationBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(if (showRegister) Res.string.already_have_account_question else Res.string.no_account_question),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(onClick = onAuthTypeChanged) {
                    Text(
                        text = stringResource(if (showRegister) Res.string.sign_in_now else Res.string.register_now),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun OAuthButtonRow(
    onSignInGoogle: () -> Unit,
    outlinedIconBorder: BorderStroke,
    showRegister: Boolean
) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        OutlinedIconButton(
            onClick = onSignInGoogle,
            border = outlinedIconBorder
        ) {
            Image(
                imageVector = vectorResource(Res.drawable.google_icon),
                contentDescription = stringResource(if (showRegister) Res.string.sign_up_google else Res.string.sign_in_google)
            )
        }
    }
}

@Preview
@Composable
private fun SignInSurfacePreview() {
    VientosDelSurTheme {
        AuthSurface(
            email = "test@example.com",
            password = "password",
            authType = AuthScreenType.SIGN_IN,
            confirmPassword = "password",
            onEmailChanged = {},
            onPasswordChanged = {},
            onSignIn = {},
            onSignUp = {},
            onSignInGoogle = {},
            onConfirmPasswordChanged = {},
            onForgotPassword = {},
            onAuthTypeChanged = {},
            validationErrors = AuthValidationErrors()
        )
    }
}

@Preview
@Composable
private fun SignInErrorsSurfacePreview() {
    VientosDelSurTheme {
        AuthSurface(
            email = "test@example.com",
            password = "password",
            authType = AuthScreenType.SIGN_IN,
            confirmPassword = "password",
            onEmailChanged = {},
            onPasswordChanged = {},
            onSignIn = {},
            onSignUp = {},
            onSignInGoogle = {},
            onConfirmPasswordChanged = {},
            onForgotPassword = {},
            onAuthTypeChanged = {},
            validationErrors = AuthValidationErrors(
                emailError = Res.string.invalid_email_error,
                passwordError = Res.string.credential_mismatch_error
            )
        )
    }
}


@Preview
@Composable
private fun SignUpSurfacePreview() {
    VientosDelSurTheme {
        AuthSurface(
            email = "test@example.com",
            password = "password",
            authType = AuthScreenType.SIGN_UP,
            confirmPassword = "password",
            onEmailChanged = {},
            onPasswordChanged = {},
            onSignIn = {},
            onSignUp = {},
            onSignInGoogle = {},
            onConfirmPasswordChanged = {},
            onForgotPassword = {},
            onAuthTypeChanged = {},
            validationErrors = AuthValidationErrors()
        )
    }
}

@Preview
@Composable
private fun SignUpErrorsSurfacePreview() {
    VientosDelSurTheme {
        AuthSurface(
            email = "test@example.com",
            password = "password",
            authType = AuthScreenType.SIGN_UP,
            confirmPassword = "password",
            onEmailChanged = {},
            onPasswordChanged = {},
            onSignIn = {},
            onSignUp = {},
            onSignInGoogle = {},
            onConfirmPasswordChanged = {},
            onForgotPassword = {},
            onAuthTypeChanged = {},
            validationErrors = AuthValidationErrors(
                emailError = Res.string.invalid_email_error,
                passwordError = Res.string.password_mismatch_error
            )
        )
    }
}