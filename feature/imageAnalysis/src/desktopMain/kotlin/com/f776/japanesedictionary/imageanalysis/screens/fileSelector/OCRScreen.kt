@file:OptIn(
    ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)

package com.f776.japanesedictionary.imageanalysis.screens.fileSelector

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragData
import androidx.compose.ui.draganddrop.awtTransferable
import androidx.compose.ui.draganddrop.dragData
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import java.awt.FileDialog
import java.awt.datatransfer.DataFlavor
import java.io.File
import java.net.URI

@Composable
internal fun OCRScreen(
    modifier: Modifier = Modifier,
    imagePainter: Painter?,
    onAction: (OCRAction) -> Unit,
    onGoBack: () -> Unit
) {
    var showTargetBorder by remember { mutableStateOf(false) }
    val imageFileRegex = remember { ".+\\.(jpg|jpeg|png)$".toRegex() }

    val dragAndDropTarget = remember {
        object : DragAndDropTarget {

            // Highlights the border of a potential drop target
            override fun onStarted(event: DragAndDropEvent) {
                showTargetBorder = true
            }

            override fun onEnded(event: DragAndDropEvent) {
                showTargetBorder = false
            }

            override fun onDrop(event: DragAndDropEvent): Boolean {
                // Prints the type of action into system output every time
                // a drag-and-drop operation is concluded.
                println("Action at the target: ${event.action}")
                println(event.dragData())

                return when (val data = event.dragData()) {
                    // ???
                    is DragData.Image -> {
                        true
                    }

                    is DragData.FilesList -> {
                        data.readFiles().firstOrNull()?.let { photo ->
                            onAction(OCRAction.OnImageSelected(File(URI(photo))))
                            true
                        }
                        false
                    }

                    else -> false
                }
            }
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recognize text from an image") },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Return to home"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Row(modifier = modifier.padding(innerPadding).nestedScroll(scrollBehavior.nestedScrollConnection)) {
            OutlinedCard(
                modifier = modifier
                    .padding(16.dp)
                    .weight(1f),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = if (showTargetBorder) 2.dp else 1.dp,
                    brush = if (showTargetBorder) SolidColor(MaterialTheme.colorScheme.primary) else CardDefaults.outlinedCardBorder().brush
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().dragAndDropTarget(
                        shouldStartDragAndDrop = { event ->
                            when {
                                event.awtTransferable.isDataFlavorSupported(DataFlavor.imageFlavor) -> true
                                event.awtTransferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor) -> {
                                    val files =
                                        event.awtTransferable.getTransferData(DataFlavor.javaFileListFlavor) as? List<*>
                                    files?.size == 1 && files.firstOrNull()?.toString()?.lowercase()
                                        ?.matches(imageFileRegex) == true
                                }

                                else -> false
                            }
                        },
                        target = dragAndDropTarget
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Drag and drop an image here")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
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
                                    onAction(OCRAction.OnImageSelected(file))
                                }
                            }
                        }
                    ) {
                        Text("Select Image")
                    }
                }
            }

            OutlinedCard(
                modifier = modifier
                    .padding(16.dp)
                    .weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Image preview")
                    Spacer(modifier = Modifier.height(16.dp))
                    AnimatedContent(targetState = imagePainter) { image ->
                        if (image != null) {
                            Image(painter = image, contentDescription = null)
                        } else {
                            Text("No image selected")
                        }
                    }
                }
            }
        }
    }

}

