plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.shared)
    api(projects.core.common)

    implementation(projects.server.data.imageanalysis)
    implementation(projects.server.service.housekeeping)

    implementation(libs.koin.ktor)
    implementation(libs.kotlinx.datetime)
}