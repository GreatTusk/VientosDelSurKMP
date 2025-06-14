plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.core.common)

    api(libs.postgresql)
    implementation(libs.koin.ktor)

    api(libs.exposed.core)
    implementation(libs.exposed.migration)
    api(libs.exposed.kotlin.datetime)
    api(libs.exposed.dao)
    api(libs.exposed.jdbc)

    api(libs.h2database.h2)
}