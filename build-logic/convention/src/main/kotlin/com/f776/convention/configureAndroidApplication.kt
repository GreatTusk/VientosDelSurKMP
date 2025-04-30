package com.f776.convention

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

internal fun Project.configureAndroidApplication(
    extension: ApplicationExtension,
    versionCodeProperty: Int,
    versionNameProperty: String
) = extension.apply {
    defaultConfig {
        applicationId = "com.f776.japanesedictionary"
        targetSdk = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()
        versionCode = versionCodeProperty
        versionName = versionNameProperty
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                // Includes the default ProGuard rules files that are packaged with
                // the Android Gradle plugin. To learn more, go to the section about
                // R8 configuration files.
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}