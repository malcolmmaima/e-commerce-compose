import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class TestingConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "api"(libs.findLibrary("testing.junit").get())
                "api"(libs.findLibrary("testing.instantiator").get())
                "api"(libs.findLibrary("kotlin.testing.coroutines").get())
                "api"(libs.findLibrary("testing.mockk").get())
                "api"(libs.findLibrary("testing.mock.webserver").get())
                "api"(libs.findLibrary("testing.turbine").get())
                "api"(libs.findLibrary("androidx.test.runner").get())
                 "api"(libs.findLibrary("android.test.core").get())
                add("api", (libs.findLibrary("androidx.junit.ktx").get()))
            }
        }
    }
}