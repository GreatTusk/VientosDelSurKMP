plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.shared)
    api(projects.core.common)

    implementation(projects.server.service.employee)
    api(projects.server.domain.imageanalysis)

    implementation(libs.azure.cognitiveservices.customvision.prediction)

    implementation(libs.koin.ktor)
    implementation(libs.kotlinx.datetime)
}