pluginManagement {
    plugins {
        kotlin("jvm") version "2.0.0"
        id("com.android.application") version "8.4.0"
        id("com.android.application") version "8.4.0"
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "Zombgame"
include("server")
include("client")
include("core")
include(":zombgame-android")
