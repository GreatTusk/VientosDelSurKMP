plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
    alias(libs.plugins.f776.composeMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        val mobileMain by creating {
            dependsOn(commonMain.get())
        }

        androidMain.get().dependsOn(mobileMain)
        iosMain.get().dependsOn(mobileMain)

        androidMain.dependencies {
            implementation(libs.androidx.camera.compose)
        }

        mobileMain.dependencies {
            // Permissions
            implementation(libs.moko.camera)
            implementation(libs.moko.compose)
        }

        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.mediapicker)
            implementation(projects.data.imageAnalysis)
            implementation(projects.data.room)

            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.adaptive.navigation)

            implementation(libs.koin.compose.viewmodel)

            implementation(libs.kotlinx.serialization.json)
        }
    }
}