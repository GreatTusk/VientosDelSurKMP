@file:OptIn(ExperimentalMaterial3Api::class)

package com.f776.core.ui.navigation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.f776.core.ui.theme.VientosDelSurTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RouteAwareTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    navController: NavController
) {
    val navStackEntry by navController.currentBackStackEntryAsState()

    navStackEntry?.let { entry ->
        val viewModel: TopAppBarViewModel = viewModel(
            viewModelStoreOwner = entry,
            initializer = { TopAppBarViewModel() }
        )

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        TopAppBar(
            title = {
                AnimatedContent(targetState = uiState.title) {
                    it()
                }
            },
            modifier = modifier,
            navigationIcon = {
                IconButton(onClick = uiState.navAction) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = uiState.navContentDescription
                    )
                }
            },
            actions = actions ?: uiState.actions,
            scrollBehavior = scrollBehavior
        )
    }

}

@Preview
@Composable
private fun RouteAwareTopAppBarPreview() {
    VientosDelSurTheme {
        RouteAwareTopAppBar(
            navController = rememberNavController(),
        )
    }
}
