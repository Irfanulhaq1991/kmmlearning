plugins {
    id("com.android.application")
    kotlin("android")
}
val composeVersion = "1.0.0-rc02"

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.learning.dogify.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }


    buildFeatures{
        compose = true
    }

    composeOptions{
        kotlinCompilerVersion = "1.5.10"
        kotlinCompilerExtensionVersion = composeVersion
    }
}



dependencies {
    implementation(project(":shared"))
    implementation("androidx.appcompat:appcompat:1.3.0")
    // Android Lifecycle
    val lifecycleVersion = "2.3.1"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha02")
    // Android Kotlin extensions
    implementation("androidx.core:core-ktx:1.6.0")
    //region Jetpack Compose
    implementation("androidx.activity:activity-compose:1.3.0-rc02")
    implementation("androidx.compose.ui:ui:$composeVersion")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.0.0-rc02")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    // Material Design
    implementation("androidx.compose.material:material:$composeVersion")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    val accompanistVersion = "0.13.0"
    implementation("com.google.accompanist:accompanist-coil:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-swiperefresh:$accompanistVersion")
    //endregion Jetpack compose
}