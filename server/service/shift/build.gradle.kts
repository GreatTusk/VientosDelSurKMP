plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.shared)
    api(projects.core.common)
    implementation(projects.server.service.employee)
    implementation(projects.server.data.employee)
    api(projects.server.domain.shift)
    implementation(libs.koin.ktor)
    implementation(libs.kotlinx.datetime)
}