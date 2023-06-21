import dev.iurysouza.modulegraph.Orientation
import dev.iurysouza.modulegraph.Theme

buildscript {
    val kotlinVersion = "1.8.0"
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.4")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }
}

plugins {
    id("com.android.application") version "7.2.0" apply false
    id("com.android.library") version "7.2.0" apply false
    kotlin("android") version "1.8.0" apply false
    id("dev.iurysouza.modulegraph") version "0.3.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.4.0"
}

moduleGraphConfig {
    readmePath.set("$projectDir/README.md")
    heading.set("## Dependency Diagram")
    theme.set(Theme.NEUTRAL)
    orientation.set(Orientation.LEFT_TO_RIGHT)
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
