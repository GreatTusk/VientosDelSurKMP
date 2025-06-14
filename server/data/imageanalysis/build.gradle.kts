plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

dependencies {
    api(projects.server.domain.imageanalysis)
    implementation(projects.server.core.database)
    implementation(libs.koin.ktor)
    implementation(libs.kotlinx.serialization.json)
    implementation(projects.core.network)
}