package com.f776.japanesedictionary.imageanalysis.screens.camera

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.camera.CAMERA
import kotlinx.coroutines.launch

internal class CameraPermissionViewModel(private val permissionsController: PermissionsController) : ViewModel() {
    private var state by mutableStateOf(PermissionState.NotDetermined)

    init {
        provideOrRequestCameraPermission()
    }

    private fun provideOrRequestCameraPermission() {
        viewModelScope.launch {
            try {
                permissionsController.providePermission(Permission.CAMERA)
                state = PermissionState.Granted
            } catch (_: DeniedAlwaysException) {
                state = PermissionState.DeniedAlways
            } catch (_: DeniedException) {
                state = PermissionState.Denied
            } catch (e: RequestCanceledException) {
                state = PermissionState.NotGranted
                e.printStackTrace()
            }
        }
    }
}