plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

dependencies {
    api(projects.server.domain.imageanalysis)
    implementation(libs.koin.ktor)
    implementation(libs.kotlinx.serialization.json)
    implementation(projects.core.network)
}