plugins {
    alias(libs.plugins.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.kotlin.kapt)
//    alias(libs.plugins.kotlin.parcelize)
//    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.translate589.study"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.translate589.study"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.bundles.androids)
    implementation(libs.bundles.navigations)
    implementation(libs.bundles.tests)
    implementation(libs.timber)
    implementation(libs.jodaTime)

    // Hilt
    implementation(libs.bundles.hilts)
    kapt(libs.bundles.hilts.compiler)
}