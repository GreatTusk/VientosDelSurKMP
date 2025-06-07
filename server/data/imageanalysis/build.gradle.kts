plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.server.domain.imageanalysis)
    implementation(libs.azure.cognitiveservices.customvision.prediction)
    implementation(libs.koin.ktor)
}