import java.io.ByteArrayOutputStream
import org.gradle.api.GradleException

plugins {
    id("ecommerceshop.app")
    id("ecommerceshop.hilt")
    id("ecommerceshop.app.compose")
    id("ecommerceshop.app.network")
}

android {
    namespace = "com.shop.app"

    defaultConfig {
        applicationId = "com.shop.app"

        var versionCode: Int
        val bytes = ByteArrayOutputStream()
        try {
            project.exec {
                commandLine = "git rev-list HEAD --count".split(" ")
                standardOutput = bytes
            }
            versionCode = String(bytes.toByteArray()).trim().toInt() + 1
        } catch (e: Exception) {
            throw GradleException("Failed to get Git commit count", e)
        }

        versionName = "0.0.$versionCode"

        vectorDrawables {
            useSupportLibrary = true
        }

        buildTypes {
            debug {
                versionNameSuffix = "-debug"
                applicationIdSuffix = ".debug"
            }
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":core:networking"))
    implementation(project(":features:shop"))

    implementation(libs.accompanist.ui.controller)
}
