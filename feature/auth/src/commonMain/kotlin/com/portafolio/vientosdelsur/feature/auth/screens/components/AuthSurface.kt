package com.portafolio.vientosdelsur.feature.auth.screens.components

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
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
    confirmPassword: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    onSignInGoogle: () -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onForgotPassword: () -> Unit // Added for forgot password functionality
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var showRegister by rememberSaveable { mutableStateOf(false) }
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
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = if (showRegister) ImeAction.Next else ImeAction.Done
                ),
                keyboardActions = if (showRegister) KeyboardActions.Default else KeyboardActions {
                    onSignIn()
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
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
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions { onSignUp() },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
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
                OutlinedIconButton(
                    onClick = { /* TODO: Implement Microsoft Sign In/Up */ },
                    border = outlinedIconBorder
                ) {
                    Image(
                        imageVector = vectorResource(Res.drawable.microsoft_icon),
                        contentDescription = stringResource(if (showRegister) Res.string.sign_up_microsoft else Res.string.sign_in_microsoft)
                    )
                }
                OutlinedIconButton(
                    onClick = { /* TODO: Implement Apple Sign In/Up */ },
                    border = outlinedIconBorder
                ) {
                    Image(
                        imageVector = vectorResource(Res.drawable.apple_icon),
                        contentDescription = stringResource(if (showRegister) Res.string.sign_up_apple else Res.string.sign_in_apple)
                    )
                }
            }

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
                TextButton(onClick = { showRegister = !showRegister }) {
                    Text(
                        text = stringResource(if (showRegister) Res.string.sign_in_now else Res.string.register_now),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AuthSurfacePreview() {
    VientosDelSurTheme {
        AuthSurface(
            email = "test@example.com",
            password = "password",
            confirmPassword = "password",
            onEmailChanged = {},
            onPasswordChanged = {},
            onSignIn = {},
            onSignUp = {},
            onSignInGoogle = {},
            onConfirmPasswordChanged = {},
            onForgotPassword = {}
        )
    }
}