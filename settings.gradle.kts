enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
includeBuild("build-logic")

pluginManagement {

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()

        maven("https://packages.jetbrains.team/maven/p/firework/dev")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://packages.jetbrains.team/maven/p/firework/dev")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "VientosDelSur"
include(":shared")

// Client
include(":composeApp")
include(":core:common")
include(":core:ui")
include(":core:network")
include(":core:resource")
include(":core:paging")
include(":data:imageAnalysis")
include(":domain:imageAnalysis")
include(":feature:imageAnalysis")
include(":feature:staff")
include(":feature:foryou")
include(":feature:room")

// Server
include(":server:app")
include(":server:core:data")
include(":server:data:employee")
include(":server:data:room")
include(":server:data:user")
include(":server:controller:employee")
include(":server:controller:room")
include(":server:controller:user")
include(":server:domain:housekeeping")