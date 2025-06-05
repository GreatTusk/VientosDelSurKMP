package com.portafolio.vientosdelsur.feature.auth.screens.signup.steps

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import vientosdelsur.feature.auth.generated.resources.kitchen_option
import vientosdelsur.feature.auth.generated.resources.kitchen_support_option
import vientosdelsur.feature.auth.generated.resources.on_call_option

internal enum class HousekeeperRoleOption(val text: StringResource, val icon: ImageVector) {
    KITCHEN(Res.string.kitchen_option, Icons.Default.Kitchen),
    KITCHEN_SUPPORT(Res.string.kitchen_support_option, Icons.Default.Support),
    ON_CALL(Res.string.on_call_option, Icons.Default.Call)
}

@Composable
internal fun HousekeeperRoleStep(
    modifier: Modifier = Modifier,
    roleOption: HousekeeperRoleOption?,
    onRoleSelected: (HousekeeperRoleOption) -> Unit,
    onContinue: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize().padding(horizontal = 32.dp)) {
        Text(
            text = "¿Cuál es tu rol?",
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HousekeeperRoleOption.entries.forEach { option ->
                Card(
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = if (option == roleOption)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline,
                        shape = CardDefaults.elevatedShape
                    ),
                    colors = CardDefaults.cardColors()
                        .copy(
                            containerColor =
                                if (roleOption == option)
                                    MaterialTheme.colorScheme.secondaryContainer
                                else MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                    onClick = { onRoleSelected(option) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                    ) {
                        RadioButton(
                            selected = roleOption == option,
                            onClick = { onRoleSelected(option) }
                        )
                        Text(
                            text = stringResource(option.text),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 8.dp).weight(1f)
                        )
                        Icon(
                            imageVector = option.icon,
                            contentDescription = null,
                            tint = if (roleOption == option)
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
            enabled = roleOption != null
        ) {
            Text("Continuar")
        }
    }
}

@Preview
@Composable
private fun HousekeeperRoleStepPreview() {
    VientosDelSurTheme {
        ProgressScaffold(
            onGoBack = {},
            onSkipStep = null,
            progress = { 0.1f },
            content = {
                HousekeeperRoleStep(
                    modifier = Modifier.padding(it),
                    onContinue = {},
                    roleOption = HousekeeperRoleOption.KITCHEN,
                    onRoleSelected = {}
                )
            }
        )
    }
}