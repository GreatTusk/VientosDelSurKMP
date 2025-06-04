package com.portafolio.vientosdelsur.feature.auth.screens.signup.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.awt.ComposeWindow
import com.portafolio.vientosdelsur.feature.auth.screens.signup.UserProfilePicture
import java.awt.FileDialog
import java.io.File

@Composable
internal actual fun ProfilePhoto(userProfilePicture: UserProfilePicture) {
    val imageFileRegex = remember { ".+\\.(jpg|jpeg|png)$".toRegex() }

    ProfilePhoto(
        userProfilePicture = userProfilePicture,
        onClick = {
            with(FileDialog(ComposeWindow())) {
                isVisible = true
                mode = FileDialog.LOAD

                // Set file filter for images
                setFilenameFilter { _, name ->
                    name.lowercase().matches(imageFileRegex)
                }
                file?.let { filename ->
                    val file = File(directory, filename)

                }
            }
        }
    )
}