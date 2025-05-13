plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.play.services)
            api(libs.bundles.camerax)
        }

        commonMain.dependencies {
            api(projects.domain.imageAnalysis)
            implementation(libs.koin.core)
        }
    }
}