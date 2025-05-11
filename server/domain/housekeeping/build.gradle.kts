plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    implementation(projects.shared)
    implementation(projects.core.common)

    implementation(libs.kotlinx.datetime)
}