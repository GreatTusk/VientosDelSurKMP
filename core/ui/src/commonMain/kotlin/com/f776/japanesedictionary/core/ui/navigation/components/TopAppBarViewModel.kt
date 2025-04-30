@file:OptIn(ExperimentalMaterial3Api::class)

package com.f776.japanesedictionary.core.ui.navigation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

internal class TopAppBarViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TopAppBarUiState())
    val uiState = _uiState.asStateFlow()

    fun updateState(topAppBarUiState: TopAppBarUiState) {
        if (_uiState.value.titleText != topAppBarUiState.titleText) {
            _uiState.update { it.copy(title = topAppBarUiState.title, titleText = topAppBarUiState.titleText) }
        }

        _uiState.update { it.copy(navAction = topAppBarUiState.navAction, navContentDescription =  topAppBarUiState.navContentDescription) }
    }
}