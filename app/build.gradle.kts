plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.google.gms.google.services)


}

android {
    namespace = "com.example.nukanote"
    compileSdk = 34

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.ui.desktop)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //rooms
    implementation (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler) // For annotation processing
    implementation (libs.androidx.room.ktx)

    //DI
    // Dagger Core
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")

// Dagger Android
    api (libs.google.dagger.android)
    api (libs.google.dagger.android.support)
    kapt (libs.google.dagger.android.processor)

// Dagger - Hilt
    implementation (libs.dagger.hilt.android)
    kapt (libs.hilt.android.compiler)

    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1") // Latest version
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
}