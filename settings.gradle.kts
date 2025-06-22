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
include(":core:mediapicker")
include(":data:imageAnalysis")
include(":data:room")
include(":data:employee")
include(":data:auth")
include(":data:shift")
include(":domain:imageAnalysis")
include(":domain:room")
include(":domain:auth")
include(":domain:employee")
include(":domain:shift")
include(":feature:imageAnalysis")
include(":feature:staff")
include(":feature:foryou")
include(":feature:room")
include(":feature:hotel")
include(":feature:auth")
include(":feature:shift")
include(":feature:shiftadmin")

// Server
include(":server:app")
include(":server:core:database")
include(":server:core:controller")
include(":server:core:firebase")
include(":server:domain:housekeeping")
include(":server:domain:room")
include(":server:domain:employee")
include(":server:domain:user")
include(":server:domain:shift")
include(":server:domain:imageanalysis")
include(":server:data:employee")
include(":server:data:room")
include(":server:data:housekeeping")
include(":server:data:user")
include(":server:data:shift")
include(":server:data:imageanalysis")
include(":server:service:housekeeping")
include(":server:service:shift")
include(":server:service:employee")
include(":server:service:imageanalysis")
include(":server:controller:employee")
include(":server:controller:shift")
include(":server:controller:room")
include(":server:controller:user")
include(":server:controller:cron")
include(":server:controller:imageanalysis")
