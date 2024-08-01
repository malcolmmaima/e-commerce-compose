package com.shop

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureNetworking(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            add("implementation", (libs.findLibrary("retrofit").get()))
            add("implementation", (libs.findLibrary("retrofit.converter").get()))
            add("implementation", (libs.findLibrary("okhttp").get()))
            add("implementation", (libs.findLibrary("moshi").get()))
            add("implementation", (libs.findLibrary("moshi.retrofit").get()))
            add("implementation", (libs.findLibrary("slack.eithernet").get()))
            add("testImplementation", (libs.findLibrary("slack.eithernet").get()))
            add("implementation", (libs.findLibrary("okhttp.logging").get()))
            add("ksp", (libs.findLibrary("moshi.codegen").get()))
        }
    }
}