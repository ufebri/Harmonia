import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.parcelize)
    id("jacoco")
}

apply("../shared_dependencies.gradle")

val apikeyPropertiesFile = rootProject.file("config.properties")
val apikeyProperties = Properties().apply {
    load(apikeyPropertiesFile.inputStream())
}

android {
    namespace = "febri.uray.bedboy.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        multiDexEnabled = true
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"${apikeyProperties["BASE_URL"] as String}\"")
        buildConfigField("String", "API_KEY", "\"${apikeyProperties["API_KEY"] as String}\"")
        buildConfigField("String", "DB_NAME", "\"${apikeyProperties["DB_NAME"] as String}\"")
        buildConfigField("String", "DB_PASS", "\"${apikeyProperties["DB_PASS"] as String}\"")
        buildConfigField("String", "WEB_CLIENT_ID", "\"${apikeyProperties["WEB_CLIENT_ID"] as String}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf("-opt-in=kotlin.RequiresOptIn")
    }

    buildFeatures {
        buildConfig = true
    }
}

jacoco {
    toolVersion = "0.8.8"
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
    implementation(libs.kotlin.stdlib)

    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    androidTestImplementation(libs.androidx.room.testing)


    //retrofit
    api(libs.retrofit)
    api(libs.converterGson)
    api(libs.loggingInterceptor)

    //coroutine
    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)

    api(libs.androidx.lifecycle)
    api(libs.androidx.activityKTX)
    api(libs.androidx.fragmentKTX)
    api(libs.androidx.datastore)

    //paging
    api(libs.androidx.pagingRuntime)

    implementation(libs.android.database.sqlcipher)
    implementation(libs.androidx.sqlite)

}