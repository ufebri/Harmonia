import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    id("jacoco")
}

apply("../shared_dependencies.gradle")

val apikeyPropertiesFile = rootProject.file("config.properties")
val apikeyProperties = Properties().apply {
    load(apikeyPropertiesFile.inputStream())
}

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

    signingConfigs {
        create("release") {
            storeFile = file(apikeyProperties.getProperty("KEYSTORE_FILE"))
            storePassword = apikeyProperties.getProperty("KEYSTORE_PASSWORD")
            keyAlias = apikeyProperties.getProperty("KEY_ALIAS")
            keyPassword = apikeyProperties.getProperty("KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            isMinifyEnabled = false
            enableUnitTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += listOf("-opt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        viewBinding = true
        mlModelBinding = true
    }
}

jacoco {
    toolVersion = "0.8.10"
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