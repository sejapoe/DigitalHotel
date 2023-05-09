// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.hilt.android.gradle.plugin) apply false
    alias(libs.plugins.com.google.services) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
}

buildscript {
    dependencies {
        classpath(libs.navigation.safe.args.gradle.plugin)
    }
}

//dependencies {
//
//}