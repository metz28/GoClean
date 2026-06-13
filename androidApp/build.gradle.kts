plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation("androidx.activity:activity-compose:1.8.2")
                implementation(compose.material3)
            }
        }
    }
}

android {
    namespace = "com.goclean.app"
    compileSdk = (findProperty("ANDROID_COMPILE_SDK") as String).toInt()

    defaultConfig {
        applicationId = "com.goclean.app"
        minSdk = (findProperty("ANDROID_MIN_SDK") as String).toInt()
        targetSdk = (findProperty("ANDROID_TARGET_SDK") as String).toInt()
        versionCode = (findProperty("VERSION_CODE") as String).toInt()
        versionName = findProperty("VERSION_NAME") as String
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
}
