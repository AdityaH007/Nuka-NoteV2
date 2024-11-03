plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.google.gms.google.services)
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.nukanote"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.nukanote"
        minSdk = 28
        targetSdk = 34
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
}

dependencies {


    implementation(libs.firebase.auth)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    kapt (libs.artifactid)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //rooms
    implementation (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler) // For annotation processing
    implementation (libs.androidx.room.ktx)

    //DI
    // Dagger Core
    implementation (libs.dagger)
    kapt (libs.dagger.compiler)

// Dagger Android
    api (libs.google.dagger.android)
    api (libs.google.dagger.android.support)
    kapt (libs.google.dagger.android.processor)

// Dagger - Hilt
    implementation (libs.dagger.hilt.android)
    kapt (libs.hilt.android.compiler)
}