plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            implementation(projects.domain.employee)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}