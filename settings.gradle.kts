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
include(":data:room")
include(":data:employee")
include(":domain:imageAnalysis")
include(":domain:room")
include(":domain:employee")
include(":feature:imageAnalysis")
include(":feature:staff")
include(":feature:foryou")
include(":feature:room")
include(":feature:hotel")

// Server
include(":server:app")
include(":server:core:database")
include(":server:domain:housekeeping")
include(":server:domain:employee")
include(":server:domain:shift")
include(":server:data:employee")
include(":server:data:room")
include(":server:data:user")
include(":server:data:shift")
include(":server:service:housekeeping")
include(":server:service:shift")
include(":server:service:employee")
include(":server:controller:employee")
include(":server:controller:shift")
include(":server:controller:room")
include(":server:controller:user")
include(":server:controller:cron")
