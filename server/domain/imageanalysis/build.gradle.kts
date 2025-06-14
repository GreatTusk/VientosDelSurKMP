plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.core.common)
    implementation(projects.server.domain.room)
    api(libs.kotlinx.datetime)
}