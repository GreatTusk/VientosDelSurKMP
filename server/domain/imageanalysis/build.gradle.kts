plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.core.common)
    implementation(projects.server.domain.room)
    implementation(projects.server.domain.employee)
    api(libs.kotlinx.datetime)
}