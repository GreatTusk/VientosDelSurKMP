package com.portafolio.vientosdelsur.core.mediapicker.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.awt.ComposeWindow
import com.portafolio.vientosdelsur.core.mediapicker.data.PhotoProvider
import com.portafolio.vientosdelsur.core.mediapicker.model.Photo
import java.awt.FileDialog
import java.io.File

@Composable
actual fun PickedPhoto(
    photo: Photo,
    photoProvider: PhotoProvider
) {
    val imageFileRegex = remember { ".+\\.(jpg|jpeg|png)$".toRegex() }

    PickedPhoto(
        photo = photo,
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
                    photoProvider.onPhotoSelected(file)
                }
            }
        }
    )
}