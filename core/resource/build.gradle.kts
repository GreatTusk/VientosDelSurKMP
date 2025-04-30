plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
    alias(libs.plugins.f776.composeMultiplatform)
}

compose.resources {
    publicResClass = true
    generateResClass = auto
    packageOfResClass = "com.f776.japanesedictionary.core.resource"
}
