plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            api(projects.shared)
            api(libs.kotlinx.datetime)
        }
    }
}