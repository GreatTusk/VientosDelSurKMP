plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.core.common)
    implementation(projects.domain.room)
    api(libs.kotlinx.datetime)
}