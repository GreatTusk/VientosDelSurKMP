package com.f776.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureKotlinAndroid(
    extension: CommonExtension<*, *, *, *, *, *>,
) = extension.apply {

    //get module name from module path
    val moduleName = path.split(":")
        .drop(1)
        .joinToString(".")

    namespace =
        if (moduleName.isNotEmpty()) {
            if (moduleName == "composeApp") {
                "com.portafolio.vientosdelsur"
            } else {
                "com.portafolio.vientosdelsur.$moduleName"
            }
        } else "com.f776"

    compileSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()
    defaultConfig {
        minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    packaging {
        resources {
            // Before kuromoji
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/{AL2.0,LGPL2.1,CONTRIBUTORS.md,LICENSE.md}"
        }
    }
}