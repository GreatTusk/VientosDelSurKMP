package com.portafolio.vientosdelsur.feature.auth.screens.signup.steps

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.feature.auth.screens.signup.components.ProgressScaffold
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import vientosdelsur.feature.auth.generated.resources.Res
import vientosdelsur.feature.auth.generated.resources.admin_option
import vientosdelsur.feature.auth.generated.resources.housekeeper_option
import vientosdelsur.feature.auth.generated.resources.supervisor_option

internal enum class OccupationOption(val text: StringResource, val icon: ImageVector) {
    HOUSEKEEPER(Res.string.housekeeper_option, Icons.Default.CleaningServices),
    SUPERVISOR(Res.string.supervisor_option, Icons.Default.SupervisorAccount),
    ADMIN(Res.string.admin_option, Icons.Default.AdminPanelSettings)
}

@Composable
internal fun OccupationStep(
    modifier: Modifier = Modifier,
    occupationOption: OccupationOption?,
    onOccupationSelected: (OccupationOption) -> Unit,
    onContinue: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize().padding(horizontal = 32.dp)) {
        Text(
            text = "¿Cuál es tu puesto?",
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OccupationOption.entries.forEach { option ->
                Card(
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = if (option == occupationOption)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline,
                        shape = CardDefaults.elevatedShape
                    ),
                    colors = CardDefaults.cardColors()
                        .copy(
                            containerColor =
                                if (occupationOption == option)
                                    MaterialTheme.colorScheme.secondaryContainer
                                else MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                    onClick = { onOccupationSelected(option) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                    ) {
                        RadioButton(
                            selected = occupationOption == option,
                            onClick = { onOccupationSelected(option) }
                        )
                        Text(
                            text = stringResource(option.text),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 8.dp).weight(1f)
                        )
                        Icon(
                            imageVector = option.icon,
                            contentDescription = null,
                            tint = if (occupationOption == option)
                                MaterialTheme.colorScheme.onSecondaryContainer
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 24.dp)
                .align(Alignment.BottomCenter),
            enabled = occupationOption != null
        ) {
            Text("Continuar")
        }
    }
}

@Preview
@Composable
private fun OccupationStepPreview() {
    VientosDelSurTheme {
        ProgressScaffold(
            onGoBack = {},
            onSkipStep = null,
            progress = { 0.1f },
            content = {
                OccupationStep(
                    modifier = Modifier.padding(it),
                    onContinue = {},
                    occupationOption = OccupationOption.HOUSEKEEPER,
                    onOccupationSelected = {}
                )
            }
        )
    }
}