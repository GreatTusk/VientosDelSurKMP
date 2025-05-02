enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "server"
include(":app")
include(":data:employee")
include(":data:room")
include(":data:user")
include(":controller:employee")
include(":controller:room")
include(":controller:user")

include(":shared")
project(":shared").projectDir = File(rootProject.projectDir, "../shared")