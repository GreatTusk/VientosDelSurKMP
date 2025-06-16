plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
    alias(libs.plugins.f776.composeMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.data.employee)
            implementation(projects.core.ui)
            implementation(projects.core.resource)
            implementation(projects.data.shift)

            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.adaptive.navigation)

            implementation(libs.koin.compose.viewmodel)

            implementation(libs.kotlinx.serialization.json)
        }
    }
}