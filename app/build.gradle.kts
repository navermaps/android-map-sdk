plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(31)

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        applicationId = "com.naver.maps.map.demo"
        minSdkVersion(15)
        targetSdkVersion(31)
        versionCode = 3130000
        versionName = "3.13.0"
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
    implementation("com.naver.maps:map-sdk:3.13.0")
}
