package com.portafolio.vientosdelsur.foryou.screens.foryou.admin

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.domain.employee.Employee
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun AdminForYouScreenRoot(modifier: Modifier = Modifier, employee: Employee) {
    AdminForYouScreen(modifier = modifier)
}

@Composable
private fun AdminForYouScreen(modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun AdminForYouScreenPreview() {
    VientosDelSurTheme {
        AdminForYouScreen()
    }
}