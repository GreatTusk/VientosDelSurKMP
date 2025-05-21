plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.core.common)
    api(projects.server.domain.employee)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.koin.test)
}