
import com.f776.convention.getVersionCodeAndNameProperty
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.desktop.DesktopExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

class DesktopApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        extensions.configure<ComposeExtension> {
            extensions.configure<DesktopExtension> {
                val (_, versionNameProperty) = getVersionCodeAndNameProperty()
                application {
                    mainClass = "com.portafolio.vientosdelsur.MainKt"

                    nativeDistributions {
                        targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                        packageName = "Vientos del Sur"
                        packageVersion = versionNameProperty
                        description = "Vientos del Sur"
                        copyright = "Copyright (c) 2025, GreatTusk"

                        windows {
                            iconFile.set(project.file("ic_launcher-playstore.ico"))
                            shortcut = true
                        }

                        linux {
                            iconFile.set(project.rootProject.file("core/resource/src/commonMain/composeResources/drawable/ic_launcher-playstore.png"))
                        }

                        macOS {
                            dockName = "Vientos del Sur"
                            entitlementsFile.set(project.file("default.entitlements"))
                            iconFile.set(project.file("ic_launcher-playstore.icns"))
                        }
                    }

                    buildTypes.release {
                        proguard {
                            optimize.set(true)
                            obfuscate.set(true)
                            configurationFiles.from("proguard-rules.pro")
                        }
                    }
                }
            }
        }
    }
}