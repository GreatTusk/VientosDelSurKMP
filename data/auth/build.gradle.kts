plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.domain.auth)
            implementation(libs.firebase.auth)
            implementation(projects.shared)
            implementation(projects.core.network)
            implementation(libs.koin.core)
        }
    }
}