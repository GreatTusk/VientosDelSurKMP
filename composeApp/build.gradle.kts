import org.jetbrains.compose.reload.ComposeHotRun

plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidApplication)
    alias(libs.plugins.f776.composeMultiplatform)
    alias(libs.plugins.f776.desktopApplication)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(projects.core.resource)
            implementation(projects.core.ui)

            implementation(projects.feature.imageAnalysis)
            implementation(projects.feature.foryou)
            implementation(projects.feature.room)

            implementation(libs.jetbrains.compose.navigation)

            implementation(libs.koin.compose.viewmodel)

            implementation(libs.kotlinx.serialization.json)
        }
    }
}

tasks.register<ComposeHotRun>("runHot") {
    mainClass.set("com.f776.japanesedictionary.MainKt")
}