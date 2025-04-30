plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            api(projects.domain.ocr)
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            implementation(libs.text.recognition.japanese)
            implementation(libs.kotlinx.coroutines.play.services)
            api(libs.bundles.camerax)
        }
    }
}