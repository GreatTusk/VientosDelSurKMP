@file:OptIn(ExperimentalComposeLibrary::class)

package com.f776.convention

import org.gradle.api.Project
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureComposeDependencies(
    extension: KotlinMultiplatformExtension,
    composeDeps: ComposePlugin.Dependencies
) = extension.apply {
    sourceSets.apply {
        commonMain.dependencies {
            implementation(composeDeps.runtime)
            implementation(composeDeps.foundation)
            implementation(composeDeps.material3AdaptiveNavigationSuite)
            implementation(composeDeps.ui)
            implementation(composeDeps.components.resources)
            implementation(composeDeps.components.uiToolingPreview)
            implementation(composeDeps.material3)
            implementation(composeDeps.materialIconsExtended)
            implementation(composeDeps.uiTest)
            implementation(libs.findLibrary("androidx-lifecycle-viewmodel").get())
            implementation(libs.findLibrary("ui-backhandler").get())
            implementation(libs.findLibrary("androidx-lifecycle-runtime-compose").get())
            implementation(libs.findLibrary("adaptive.layout").get())
        }

        findByName("androidMain")?.dependencies {
            implementation(composeDeps.preview)
            implementation(libs.findLibrary("androidx.activity.compose").get())
        }

        findByName("desktopMain")?.dependencies {
            implementation(composeDeps.desktop.currentOs)
            implementation(libs.findLibrary("kotlinx.coroutines.swing").get())
        }
    }
}