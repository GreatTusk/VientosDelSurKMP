plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    implementation(projects.server.core.database)
    api(projects.server.domain.shift)
    api(projects.server.domain.housekeeping)

    implementation(libs.koin.ktor)
    implementation(libs.exposed.core)
    implementation(libs.exposed.kotlin.datetime)

    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2database.h2)
}