plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
    alias(libs.plugins.f776.composeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.adaptive.navigation)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.material.kolor)
        }

        desktopMain.dependencies {
            implementation(libs.jna)
            implementation(libs.jna.platform)
        }
    }
}