
import com.f776.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class GlanceWidgetConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("composeCompiler").get().get().pluginId)
        }

        val composeDeps = extensions.getByType<ComposeExtension>().dependencies

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.androidMain.dependencies {
                implementation(composeDeps.runtime)
                implementation(composeDeps.foundation)
                implementation(libs.findLibrary("androidx.activity.compose").get())
                api(libs.findLibrary("glance-material").get())
                api(libs.findLibrary("glance-appwidget").get())
                api(libs.findLibrary("glance-appwidget-preview").get())
                api(libs.findLibrary("glance-preview").get())
            }
        }
    }
}