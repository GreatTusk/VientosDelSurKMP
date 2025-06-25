import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.*

plugins {
    alias(libs.plugins.f776.kotlinMultiplatform)
    alias(libs.plugins.f776.androidLibrary)
    alias(libs.plugins.build.konfig)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.credentials)
            implementation(libs.androidx.credentials.play.services.auth)
            implementation(libs.googleid)
            implementation(libs.play.services.auth)
        }

        commonMain.dependencies {
            api(projects.domain.auth)
            api(projects.domain.employee)
            implementation(libs.firebase.auth)
            implementation(projects.shared)
            implementation(projects.core.network)
            implementation(libs.koin.core)
        }
    }
}

buildkonfig {
    val secretKeyProperties by lazy {
        val secretKeyPropertiesFile = rootProject.file("secrets.properties")
        Properties().apply { secretKeyPropertiesFile.inputStream().use { secret -> load(secret) } }
    }
    packageName = "com.portafolio.vientosdelsur.data.auth"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "GOOGLE_OAUTH_CLIENT_ID", "${secretKeyProperties["GOOGLE_OAUTH_CLIENT_ID"]}")
    }

    targetConfigs {
        create("desktop") {
            buildConfigField(FieldSpec.Type.STRING, "DEMO_ROLE", "${secretKeyProperties["DEMO_ROLE"]}")
        }
    }
    exposeObjectWithName = "BuildConfig"
}