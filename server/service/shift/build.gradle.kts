plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.shared)
    api(projects.core.common)
    implementation(projects.server.data.employee)
    implementation(projects.server.data.shift)
    implementation(libs.koin.ktor)
    implementation(libs.kotlinx.datetime)
}