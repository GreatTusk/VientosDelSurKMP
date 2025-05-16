plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.core.common)
    implementation(libs.kotlinx.datetime)
    testImplementation(libs.kotlin.test)
}