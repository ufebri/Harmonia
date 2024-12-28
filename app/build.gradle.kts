plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    id("jacoco")
}

apply("../shared_dependencies.gradle")

android {
    namespace = "id.daydream.harmonia"
    compileSdk = 34

    defaultConfig {
        applicationId = "id.daydream.harmonia"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableUnitTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        mlModelBinding = true
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    executionData.setFrom(fileTree(buildDir).include("jacoco/testDebugUnitTest.exec"))
    sourceDirectories.setFrom(files("$projectDir/src/main/java"))
    classDirectories.setFrom(files("$buildDir/tmp/kotlin-classes/debug"))
}

dependencies {

    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.lottie)
    implementation(libs.glide)
    implementation(libs.androidx.splashScreen)
    implementation(libs.androidx.navigation.fragmentKTX)
    implementation(libs.androidx.navigation.uiKTX)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)

    implementation(project(":core"))
    implementation(project(":uicomponent"))

    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.support)
}