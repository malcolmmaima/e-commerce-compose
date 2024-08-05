plugins {
    id("ecommerceshop.library")
    id("ecommerceshop.library.compose")
    id("ecommerceshop.testing")
}

android {
    namespace = "com.shop.utils"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
