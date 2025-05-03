plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    implementation(projects.server.data.employee)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.koin.ktor)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}