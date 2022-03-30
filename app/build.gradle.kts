plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 31

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        applicationId = "com.naver.maps.map.demo"
        minSdk = 15
        targetSdk = 31
        versionCode = 3140100
        versionName = "3.14.1"
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
    implementation(kotlin("stdlib-jdk7:1.5.31"))
    implementation("com.android.support:support-v4:28.0.0")
    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.android.support:design:28.0.0")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    implementation("com.google.android.gms:play-services-location:16.0.0")
    implementation("com.naver.maps:map-sdk:3.14.1")
}
