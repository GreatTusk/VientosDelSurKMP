plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.shared)
    api(projects.core.common)

    implementation(projects.server.service.employee)
    implementation(projects.server.data.shift)
    implementation(projects.server.data.room)
    implementation(projects.server.data.housekeeping)
    implementation(libs.koin.ktor)
    api(libs.kotlinx.datetime)
}