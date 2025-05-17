plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.core.common)
    api(projects.server.domain.employee)
    implementation(libs.kotlinx.datetime)
}