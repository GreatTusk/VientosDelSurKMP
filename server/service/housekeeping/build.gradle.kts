plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.shared)
    implementation(projects.server.data.room)
    implementation(libs.koin.ktor)
    implementation(libs.kotlinx.datetime)
}