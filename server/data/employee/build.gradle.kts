plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.shared)
    api(projects.core.common)
    implementation(projects.server.core.data)

    implementation(libs.postgresql)
    implementation(libs.koin.ktor)
    implementation(libs.exposed.core)
    implementation(libs.exposed.kotlin.datetime)

    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2database.h2)
}