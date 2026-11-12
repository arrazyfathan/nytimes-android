plugins {
    `kotlin-dsl`
}

group = "com.arrazyfathan.nytimes.buildlogic"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "nytimes.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "nytimes.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}
