plugins {
    id("ecommerceshop.library")
    id("ecommerceshop.hilt")
    id("ecommerceshop.library.network")
}

android {
    namespace = "com.shop.core.features.networking"
}

dependencies {
    implementation(project(":core:utils"))
}
