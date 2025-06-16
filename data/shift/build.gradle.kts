plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared)
            implementation(projects.core.network)
            implementation(libs.koin.core)
            api(projects.domain.shift)
        }
    }
}