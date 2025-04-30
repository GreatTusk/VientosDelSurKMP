package com.f776.core.ui.navigation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry

@Composable
fun TopAppBarContentProvider(
    title: @Composable () -> Unit = {},
    titleText: String,
    navAction: () -> Unit = {},
    navContentDescription: String?
) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: TopAppBarViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { TopAppBarViewModel() },
        )
        LaunchedEffect(title, navAction, navContentDescription) {
            viewModel.updateState(
                TopAppBarUiState(
                    title = title,
                    titleText = titleText,
                    navAction = navAction,
                    navContentDescription = navContentDescription,
                )
            )
        }
    }
}