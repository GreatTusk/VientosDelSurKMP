plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
    alias(libs.plugins.f776.composeMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.data.auth)
            implementation(libs.kotlinx.datetime)

            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.adaptive.navigation)

            implementation(libs.koin.compose.viewmodel)

            implementation(libs.kotlinx.serialization.json)
        }
    }
}