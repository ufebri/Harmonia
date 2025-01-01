// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.parcelize) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.crashylytics) apply false
}