plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.shared)
    api(projects.core.common)
    implementation(projects.server.data.shift)
    implementation(projects.server.data.room)
    implementation(libs.koin.ktor)
    api(libs.kotlinx.datetime)
}