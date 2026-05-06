import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import dev.iurysouza.modulegraph.Orientation
import dev.iurysouza.modulegraph.Theme

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.7")
        classpath("com.google.gms:google-services:4.4.4")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.6")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.59.2")
    }
}

plugins {
    id("com.android.application") version "9.1.1" apply false
    id("com.android.library") version "9.1.1" apply false
    id("com.android.legacy-kapt") version "9.1.1" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10" apply false
    id("dev.iurysouza.modulegraph") version "0.3.0"
    id("com.diffplug.spotless") version "6.17.0" apply false
}

val ktlintVersion = "0.49.1"

subprojects {
    fun configureSpotless() {
        apply<SpotlessPlugin>()
        extensions.configure<SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude("**/build/**/*.kt")
                ktlint().apply {
                    setEditorConfigPath("${project.rootDir}/spotless/.editorconfig")
                }
            }
        }
    }

    pluginManager.withPlugin("com.android.application") {
        configureSpotless()
    }
    pluginManager.withPlugin("com.android.library") {
        configureSpotless()
    }
}

moduleGraphConfig {
    readmePath.set("$projectDir/README.md")
    heading.set("## Dependency Diagram")
    theme.set(Theme.NEUTRAL)
    orientation.set(Orientation.LEFT_TO_RIGHT)
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.layout.buildDirectory)
    }
}
