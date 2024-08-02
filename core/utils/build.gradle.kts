plugins {
    id("ecommerceshop.library")
    id("ecommerceshop.library.compose")
}

android {
    namespace = "com.shop.utils"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
