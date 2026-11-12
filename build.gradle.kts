import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import dev.iurysouza.modulegraph.Orientation
import dev.iurysouza.modulegraph.Theme

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.legacy.kapt) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.modulegraph)
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.navigation.safeargs.kotlin) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.hilt.android) apply false
}

val ktlintVersion = libs.versions.ktlint.get()

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
