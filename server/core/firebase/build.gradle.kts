plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    api(libs.firebase.admin)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(libs.koin.ktor)
}