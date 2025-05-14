plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.domain.employee)
            implementation(projects.shared)
            implementation(projects.core.network)
            implementation(libs.koin.core)
        }
    }
}