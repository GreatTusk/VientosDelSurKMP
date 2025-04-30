package com.f776.convention

import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.withType

fun Project.configureNativeLibs() {
    val os = determineOS()

    val nativePrefsProject =
        this.findProject(":native:core:systemPrefs") ?: error("Can't find :native:core:systemPrefs")

    val coreUi = this.findProject(":core:ui") ?: error("Can't find :core:ui")


    coreUi.tasks.withType<Jar> {
        when (os) {
            OS.WIN -> {
                from(nativePrefsProject.file("build/bin/mingwX64/releaseShared")) {
                    include("libaccent_color.dll")
                    into("natives/win32-x86-64")
                }
            }

            OS.LINUX -> {
                from(nativePrefsProject.file("build/bin/linuxX64/releaseShared")) {
                    include("libaccent_color.so")
                    into("natives/linux-x86-64")
                }
            }

            OS.MACOS -> {
                from(nativePrefsProject.file("build/bin/macosX64/releaseShared")) {
                    include("libaccent_color.dylib")
                    into("natives/darwin-x86-64")
                }
            }
        }
    }
}