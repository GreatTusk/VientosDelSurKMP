plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    implementation(libs.postgresql)
    implementation(libs.koin.ktor)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2database.h2)
}