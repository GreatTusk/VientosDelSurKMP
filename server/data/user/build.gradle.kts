plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.server.domain.user)
    implementation(projects.server.core.firebase)

    implementation(libs.postgresql)
    implementation(libs.koin.ktor)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2database.h2)
}