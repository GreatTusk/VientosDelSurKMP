package com.f776.convention

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureSourceSets(
    extension: KotlinMultiplatformExtension,
    includeDesktop: Boolean = true,
    includeIos: Boolean = true
) = extension.apply {
    jvmToolchain(17)

    if (includeDesktop) {
        jvm("desktop")
    }

    if (includeIos) {
        configureIosTargets(this)
    }

    applyDefaultHierarchyTemplate()
}

internal fun Project.configureCommonSourceSets(extension: KotlinMultiplatformExtension) = extension.apply {
    sourceSets.all {
        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }
    }

    sourceSets.commonTest.dependencies {
        implementation(kotlin("test"))
    }
}

internal fun Project.configureIosTargets(
    kotlinMultiplatformExtension: KotlinMultiplatformExtension
) {
    val baseNameIos = path.split(":").drop(2).joinToString()

    listOf(
        kotlinMultiplatformExtension.iosX64(),
        kotlinMultiplatformExtension.iosArm64(),
        kotlinMultiplatformExtension.iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = baseNameIos
            isStatic = true
        }
    }
}