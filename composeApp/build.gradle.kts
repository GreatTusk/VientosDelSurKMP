import org.jetbrains.compose.reload.ComposeHotRun

plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidApplication)
    alias(libs.plugins.f776.composeMultiplatform)
    alias(libs.plugins.f776.desktopApplication)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.google.services)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.androidx.core.splashscreen)
        }

        commonMain.dependencies {
            implementation(projects.core.resource)
            implementation(projects.core.ui)

            implementation(projects.feature.auth)
            implementation(projects.feature.foryou)
            implementation(projects.feature.hotel)
            implementation(projects.feature.imageAnalysis)
            implementation(projects.feature.room)

            implementation(projects.domain.auth)

            implementation(libs.jetbrains.compose.navigation)

            implementation(libs.koin.compose.viewmodel)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.firebase.auth)
        }
    }
}

tasks.register<ComposeHotRun>("runHot") {
    mainClass.set("com.portafolio.vientosdelsur.MainKt")
}