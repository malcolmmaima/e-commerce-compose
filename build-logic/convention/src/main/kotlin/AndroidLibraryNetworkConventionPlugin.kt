import com.android.build.gradle.LibraryExtension
import com.shop.configureNetworking
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryNetworkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("com.google.devtools.ksp")
            }
            val extension = extensions.getByType<LibraryExtension>()
            configureNetworking(extension)
        }
    }
}