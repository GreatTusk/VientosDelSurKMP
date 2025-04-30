import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.f776.buildlogic"

// Configure the build-logic plugins to target JDK 17
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}


gradlePlugin {
    plugins {
        register("composeMultiplatform"){
            id = libs.plugins.f776.composeMultiplatform.get().pluginId
            implementationClass = "ComposeConventionPlugin"
        }

        register("glanceWidget"){
            id = libs.plugins.f776.glanceWidget.get().pluginId
            implementationClass = "GlanceWidgetConventionPlugin"
        }

        register("kotlinMultiplatform"){
            id = libs.plugins.f776.kotlinMultiplatform.get().pluginId
            implementationClass = "KotlinMultiplatformConventionPlugin"
        }

        register("androidLibrary"){
            id = libs.plugins.f776.androidLibrary.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidOnly"){
            id = libs.plugins.f776.androidOnly.get().pluginId
            implementationClass = "AndroidOnlyConventionPlugin"
        }

        register("androidApplication"){
            id = libs.plugins.f776.androidApplication.get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("desktopApplication"){
            id = libs.plugins.f776.desktopApplication.get().pluginId
            implementationClass = "DesktopApplicationConventionPlugin"
        }

        register("roomMultiplatform"){
            id = libs.plugins.f776.roomMultiplatform.get().pluginId
            implementationClass = "RoomDatabaseConventionPlugin"
        }
    }
}