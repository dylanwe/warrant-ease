plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
}

// apply plugin: "com.google.devtools.ksp"

android {
    namespace = "com.warrantease.androidapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.warrantease.androidapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        // For KSP
        getByName("debug") {
            sourceSets {
                getByName("main") {
                    kotlin.srcDir("${buildDir.absolutePath}/generated/ksp/debug/kotlin")
                }
                getByName("test") {
                    kotlin.srcDir("${buildDir.absolutePath}/generated/ksp/test/kotlin")
                }
            }
        }
        getByName("release") {
            sourceSets {
                getByName("main") {
                    kotlin.srcDir("${buildDir.absolutePath}/generated/ksp/release/kotlin")
                }
            }
        }
    }

    // For KSP
    applicationVariants.all {
        kotlin.sourceSets {
            main {
                kotlin.srcDir("${buildDir.absolutePath}/generated/ksp/debug/kotlin")
            }
            test {
                kotlin.srcDir("${buildDir.absolutePath}/generated/ksp/test/kotlin")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val compose_version = "1.2.0"
    val koin_version = "3.3.3"
    val koin_ksp_version = "1.1.1"
    val koin_compose_version = "3.4.2"
    val directions_version = "1.7.33-beta"
    val ktor_version = "2.0.0"

    implementation("com.google.firebase:firebase-bom:31.3.0")
    implementation("com.google.firebase:firebase-auth-ktx:21.0.3")
    implementation("com.google.android.gms:play-services-auth:20.4.1")

    // View-model
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$compose_version")
    implementation("androidx.compose.runtime:runtime-livedata:$compose_version")

    // Compose - Navigation
    implementation("io.github.raamcosta.compose-destinations:core:$directions_version")
    implementation("io.github.raamcosta.compose-destinations:animations-core:$directions_version")
    ksp("io.github.raamcosta.compose-destinations:ksp:$directions_version")

    // Ktor client
    // Networking
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("io.ktor:ktor-client-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

    // Serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.4.1")

    // Image loading
    implementation("io.coil-kt:coil:2.1.0")
    implementation("io.coil-kt:coil-compose:2.1.0")

    // Koin DI
    implementation("io.insert-koin:koin-android:$koin_version")
    implementation("io.insert-koin:koin-androidx-compose:$koin_compose_version")
    implementation("io.insert-koin:koin-annotations:$koin_ksp_version")
    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.compose.material3:material3:1.0.0-alpha14")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
}