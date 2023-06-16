@file:Suppress("UnstableApiUsage")

import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
}

val localPropsFile = rootProject.file("local.properties")
val localProps = Properties().apply {
    load(localPropsFile.inputStream())
}
val apiKey = localProps.getProperty("API_KEY")
val keystoreFile = rootProject.file("nytimes-keystore")

android {
    compileSdk = DefaultConfig.compileSdkVersion

    defaultConfig {
        applicationId = DefaultConfig.applicationId
        minSdk = DefaultConfig.minSdkVersion
        targetSdk = DefaultConfig.targetSdkVersion
        versionCode = DefaultConfig.versionCode
        versionName = DefaultConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", apiKey)
    }

    signingConfigs {
        create("release") {
            storeFile = keystoreFile
            storePassword = "nytimes123"
            keyAlias = "nytimes-key"
            keyPassword = "nytimes123"
        }
    }

    buildTypes {
        /* release {
             minifyEnabled false
             debuggable false
             signingConfig signingConfigs.release
             proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
         }*/

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1"
    }
    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
}

dependencies {

    implementation(project(":logging"))

    // Compose
    val composeBom = platform("androidx.compose:compose-bom:2023.05.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.runtime:runtime")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.foundation:foundation-layout")
    implementation("androidx.compose.material:material")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("com.google.accompanist:accompanist-themeadapter-material:0.30.1")

    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.androidJunit)
    androidTestImplementation(Dependencies.espresso)

    // Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverterGson)
    implementation(Dependencies.retrofitAdapterRxJava2)
    implementation(Dependencies.logginInterceptor)

    // Glide
    implementation(Dependencies.glide)

    // Navigation Component
    implementation(Dependencies.navigationFragmentKtx)
    implementation(Dependencies.navigationUiKtx)

    // room
    implementation(Dependencies.room)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomKtx)

    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)

    implementation(Dependencies.activityKtx)
    implementation(Dependencies.fragmentKtx)

    // Lifecycle components
    implementation(Dependencies.lifecycleExtension)
    implementation(Dependencies.viewModelKtx)
    implementation(Dependencies.liveDataKtx)
    implementation(Dependencies.lifecycleRuntime)

    // Coroutines
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)

    // Lottie
    implementation(Dependencies.lottie)

    // Time Ago
    implementation(Dependencies.timeAgo)

    // Swipe Refresh Layout
    implementation(Dependencies.swipeRefreshLayout)

    // Firebase
    implementation(platform(Dependencies.firebaseBom))

    // Crashlytics
    implementation(Dependencies.crashlytics)
    implementation(Dependencies.analytics)

    // Sticky View
    implementation(Dependencies.stickyView)

    // Coil Image loader
    implementation(Dependencies.coil)

    // Custom Tab Chrome
    implementation(Dependencies.androidBrowser)
}
