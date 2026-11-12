@file:Suppress("UnstableApiUsage")

import java.util.Properties

plugins {
    alias(libs.plugins.nytimes.android.application)
    alias(libs.plugins.navigation.safeargs.kotlin)
    alias(libs.plugins.android.legacy.kapt)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.hilt.android)
}

val localPropsFile = rootProject.file("local.properties")
val localProps = Properties().apply {
    load(localPropsFile.inputStream())
}
val apiKey = localProps.getProperty("API_KEY")
val keystoreFile = rootProject.file("nytimes-keystore")

android {
    namespace = "com.arrazyfathan.nytimes"
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
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
        compose = true
    }
    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(project(":logging"))

    // Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.animation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.livedata)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.coil.compose)
    implementation(libs.swipe.action)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.adapter.rxjava2)
    implementation(libs.okhttp.logging.interceptor)

    // Glide
    implementation(libs.glide)

    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Lifecycle components
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Lottie
    implementation(libs.lottie)

    // Time Ago
    implementation(libs.timeago)

    // Swipe Refresh Layout
    implementation(libs.androidx.swiperefreshlayout)

    // Firebase
    implementation(platform(libs.firebase.bom))

    // Crashlytics
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    // Sticky View
    implementation(libs.sticky.scrollview)

    // Coil Image loader
    implementation(libs.coil)

    // Custom Tab Chrome
    implementation(libs.androidx.browser)
}
