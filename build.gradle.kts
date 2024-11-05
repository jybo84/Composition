// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id ("com.google.dagger.hilt.android") version "2.51.1" apply false


}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")


// Test
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.2.1")
    }
}