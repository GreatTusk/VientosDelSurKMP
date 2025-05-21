plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.core.common)
    api(projects.server.domain.shift)
    api(projects.server.domain.room)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.koin.test)
}