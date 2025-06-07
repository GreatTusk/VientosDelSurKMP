plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(projects.shared)
    api(projects.core.common)

    implementation(projects.server.service.employee)
    api(projects.server.domain.imageanalysis)

    implementation(libs.azure.cognitiveservices.customvision.prediction)
    implementation(libs.kotlinx.coroutines.rx2)

//    implementation("com.azure:azure-cognitiveservices-customvision-training:1.1.0-preview.2")

    implementation(libs.koin.ktor)
    implementation(libs.kotlinx.datetime)
}