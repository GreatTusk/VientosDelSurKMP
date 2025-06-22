plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            implementation(projects.domain.employee)
            api(libs.kotlinx.datetime)
        }
    }
}