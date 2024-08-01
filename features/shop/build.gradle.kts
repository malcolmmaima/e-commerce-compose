plugins {
    id("ecommerceshop.library")
    id("ecommerceshop.hilt")
    id("ecommerceshop.library.network")
    id("ecommerceshop.library.compose")
    id("ecommerceshop.testing")
}

android {
    namespace = "com.shop.features"
}

dependencies {
    implementation(project(":core:networking"))
    implementation(project(":core:design"))
}
