plugins {
    `kotlin-dsl`
}
group = "com.shop.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    dependencies {
        compileOnly(libs.android.gradle)
        compileOnly(libs.kotlin.gradle)
        compileOnly(libs.ksp.gradlePlugin)
    }

}

gradlePlugin {
    plugins {
        register("androidApp"){
            id = "ecommerceshop.app"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary"){
            id = "ecommerceshop.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidHilt") {
            id = "ecommerceshop.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "ecommerceshop.app.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidApplicationNetwork") {
            id = "ecommerceshop.app.network"
            implementationClass = "AndroidApplicationNetworkConventionPlugin"
        }

        register("androidLibraryNetwork") {
            id = "ecommerceshop.library.network"
            implementationClass = "AndroidLibraryNetworkConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "ecommerceshop.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidTesting") {
            id = "ecommerceshop.testing"
            implementationClass = "TestingConventionPlugin"
        }
        register("androidRoom") {
            id = "ecommerceshop.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

    }
}