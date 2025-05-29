package com.portafolio.vientosdelsur.feature.auth.screens.signup.steps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.feature.auth.screens.signup.components.ProgressScaffold
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ProfileStep(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onContinue: () -> Unit,
    initialData: Employee?
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (initialData?.photoUrl != null) {
                AsyncImage(
                    modifier = Modifier.size(64.dp).clip(CircleShape),
                    model = initialData.photoUrl,
                    contentDescription = "Profile picture"
                )
            } else {
                Icon(
                    modifier = Modifier.size(64.dp).clip(CircleShape),
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile picture"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = initialData?.firstName ?: "",
                onValueChange = {},
                label = { Text("Nombre") },
                placeholder = { Text("Ingresa tu nombre") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person2, contentDescription = "Nombre")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = initialData?.lastName ?: "",
                onValueChange = {},
                label = { Text("Apellido") },
                placeholder = { Text("Ingresa tu apellido") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person3, contentDescription = "Apellido")
                }
            )
        }

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp, vertical = 24.dp)
                .align(Alignment.BottomCenter)
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
                    paddingValues = it,
                    onContinue = {},
                    initialData = null
                )
            }
        )
    }
}