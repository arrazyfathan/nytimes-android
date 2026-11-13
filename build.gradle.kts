import com.diffplug.gradle.spotless.SpotlessExtension
import dev.iurysouza.modulegraph.Orientation
import dev.iurysouza.modulegraph.Theme

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.modulegraph)
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.hilt.android) apply false
}

val ktlintVersion = libs.versions.ktlint.get()
val spotlessPluginId = libs.plugins.spotless.get().pluginId

subprojects {
    fun configureSpotless() {
        pluginManager.apply(spotlessPluginId)
        extensions.configure<SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude("**/build/**/*.kt")
                ktlint(ktlintVersion).setEditorConfigPath("${project.rootDir}/.editorconfig")
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
        group = LifecycleBasePlugin.BUILD_GROUP
        description = "Deletes the root build directory."
        delete(rootProject.layout.buildDirectory)
    }
}
