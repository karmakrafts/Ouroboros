rootProject.name = "ouroboros"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include("ouroboros-runtime")
include("ouroboros-gradle-plugin")
include("ouroboros-compiler-plugin")