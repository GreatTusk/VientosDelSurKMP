package com.portafolio.vientosdelsur.feature.auth.screens.signup.steps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.feature.auth.screens.signup.ProfilePicture
import com.portafolio.vientosdelsur.feature.auth.screens.signup.components.ProfilePhoto
import com.portafolio.vientosdelsur.feature.auth.screens.signup.components.ProgressScaffold
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import vientosdelsur.feature.auth.generated.resources.Res
import vientosdelsur.feature.auth.generated.resources.apple_icon

@Composable
internal fun ProfileStep(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit,
    firstName: String,
    lastName: String,
    profilePicture: ProfilePicture,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit
) {
    Box(modifier = modifier.fillMaxSize().padding(horizontal = 32.dp)) {
        Text(
            text = "Cuéntanos sobre ti",
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )

        Column(
            modifier = Modifier.fillMaxSize().imePadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            ProfilePhoto(profilePicture = profilePicture)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().semantics {
                    contentType = ContentType.PersonFirstName
                },
                value = firstName,
                onValueChange = onFirstNameChanged,
                label = { Text("Nombre") },
                placeholder = { Text("Ingresa tu nombre") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person2, contentDescription = "Nombre")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().semantics {
                    contentType = ContentType.PersonLastName
                },
                value = lastName,
                onValueChange = onLastNameChanged,
                label = { Text("Apellido") },
                placeholder = { Text("Ingresa tu apellido") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person2, contentDescription = "Apellido")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onContinue()
                    }
                )
            )

            Spacer(modifier = Modifier.weight(0.5f))
        }

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 24.dp)
                .align(Alignment.BottomCenter),
            enabled = firstName.isNotBlank() && lastName.isNotBlank()
        ) {
            Text("Continuar")
        }
    }
}

@Preview
@Composable
private fun ProfileStepPreview() {
    VientosDelSurTheme {
        ProgressScaffold(
            onGoBack = {},
            onSkipStep = null,
            progress = { 0.1f },
            content = {
                ProfileStep(
                    modifier = Modifier.padding(it),
                    onContinue = {},
                    onFirstNameChanged = {},
                    onLastNameChanged = {},
                    firstName = "",
                    lastName = "",
                    profilePicture = ProfilePicture.None
                )
            }
        )
    }
}

@Preview
@Composable
private fun ProfileStepURLPreview() {
    VientosDelSurTheme {
        ProgressScaffold(
            onGoBack = {},
            onSkipStep = null,
            progress = { 0.1f },
            content = {
                ProfileStep(
                    modifier = Modifier.padding(it),
                    onContinue = {},
                    onFirstNameChanged = {},
                    onLastNameChanged = {},
                    firstName = "",
                    lastName = "",
                    profilePicture = ProfilePicture.URL("")
                )
            }
        )
    }
}

@Preview
@Composable
private fun ProfileStepImagePreview() {
    VientosDelSurTheme {
        ProgressScaffold(
            onGoBack = {},
            onSkipStep = null,
            progress = { 0.1f },
            content = {
                ProfileStep(
                    modifier = Modifier.padding(it),
                    onContinue = {},
                    onFirstNameChanged = {},
                    onLastNameChanged = {},
                    firstName = "",
                    lastName = "",
                    profilePicture = ProfilePicture.Image(imageResource(Res.drawable.apple_icon))
                )
            }
        )
    }
}