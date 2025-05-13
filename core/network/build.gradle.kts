import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.*

plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
    alias(libs.plugins.build.konfig)

}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(libs.koin.core)
            api(libs.bundles.ktor)
            implementation(libs.ktor.client.cio)
        }
    }
}

buildkonfig {
    val secretKeyProperties by lazy {
        val secretKeyPropertiesFile = rootProject.file("secrets.properties")
        Properties().apply { secretKeyPropertiesFile.inputStream().use { secret -> load(secret) } }
    }
    packageName = "com.portafolio.vientosdelsur.network"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "BASE_URL", "${secretKeyProperties["BASE_URL"]}")
    }
    exposeObjectWithName = "BuildConfig"
    targetConfigs {
        create("android") {
            buildConfigField(FieldSpec.Type.STRING, "BASE_URL", "${secretKeyProperties["BASE_URL_ANDROID"]}")
        }
        create("ios") {
            buildConfigField(FieldSpec.Type.STRING, "BASE_URL", "native")
        }
    }
}