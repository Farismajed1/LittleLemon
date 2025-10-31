plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.2.21"
}

android {
    namespace = "com.example.littlelemon"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.littlelemon"
        minSdk = 27
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.9.5")

    //observeAsState
    implementation(libs.androidx.compose.runtime.livedata)

    //Room
    val room_verison = "2.8.3"
    implementation("androidx.room:room-runtime:$room_verison")
    ksp("androidx.room:room-compiler:$room_verison")

    val ktor_version = "3.1.0"

    //client
    implementation("io.ktor:ktor-client-android:$ktor_version")
    //make client understand json
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")


    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    //Json to kotlin object
    implementation("com.google.code.gson:gson:2.10.1")

    //call images from network
    implementation("io.coil-kt:coil-compose:2.6.0")

    //implementation("com.github.bumptech.glide:compose:1.0.0-alpha.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}