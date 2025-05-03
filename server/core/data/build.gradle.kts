plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.koin.ktor)
    implementation(libs.hikaricp)
    implementation(libs.exposed.core)
}