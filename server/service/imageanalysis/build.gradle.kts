plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.shared)
    api(projects.core.common)

    implementation(projects.server.domain.room)
    implementation(projects.server.data.imageanalysis)
    implementation(projects.server.service.housekeeping)
    implementation(projects.server.service.employee)
    implementation(projects.server.domain.employee)

    implementation(libs.koin.ktor)
    implementation(libs.kotlinx.datetime)
}