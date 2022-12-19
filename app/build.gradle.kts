plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.naver.maps.map.demo"
    compileSdk = 33

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        applicationId = namespace
        minSdk = 15
        targetSdk = 33
        versionCode = 3160100
        versionName = "3.16.1"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("debug") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
        }
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
            isMinifyEnabled = true
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk7:1.7.20"))
    implementation("com.android.support:support-v4:28.0.0")
    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.android.support:design:28.0.0")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    implementation("com.google.android.gms:play-services-location:16.0.0")
    implementation("com.naver.maps:map-sdk:3.16.1")
}
