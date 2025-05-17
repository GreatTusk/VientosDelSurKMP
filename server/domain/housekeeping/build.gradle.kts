plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.core.common)
    implementation(projects.server.domain.employee)
    implementation(projects.server.domain.shift)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.core)
}