package com.shop

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("androidxComposeCompiler").get().toString()
        }

        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            add("implementation", platform(bom))
            add("implementation", libs.findBundle("compose").get())
            add("debugImplementation", libs.findLibrary("compose.ui.tooling").get())
            add("implementation", libs.findLibrary("compose.ui.util").get())


            add("implementation", libs.findLibrary("compose.navigation").get())
            add("implementation", libs.findLibrary("compose.destinations.core").get())
            add("implementation", libs.findLibrary("compose.destinations.animations.core").get())
            add("ksp", libs.findLibrary("compose.destinations.ksp").get())
        }
    }
}