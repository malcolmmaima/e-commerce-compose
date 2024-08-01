import com.android.build.gradle.LibraryExtension
import com.shop.configureKotlinAndroid
import com.shop.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }


            extensions.configure<LibraryExtension> {

                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34

                buildFeatures {
                    buildConfig = true
                }


                buildTypes {

                    debug {
                        isMinifyEnabled = false
                    }
                }

            }
            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary("testing-junit").get())
                    force("org.objenesis:objenesis:2.6")
                }
            }


            dependencies {
                add("testImplementation", kotlin("test"))
                add("implementation", (libs.findLibrary("timber").get()))
            }
        }
    }
}