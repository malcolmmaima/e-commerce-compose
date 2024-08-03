plugins {
    id("ecommerceshop.library")
    id("ecommerceshop.room")
    id("ecommerceshop.hilt")
}

android {
    namespace = "com.malcolmmaima.database"
}

dependencies {
    implementation(project(":core:networking"))
}
