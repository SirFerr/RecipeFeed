plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.recipefeed"
    compileSdk = 34

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
    defaultConfig {
        applicationId = "com.example.recipefeed"
        minSdk = 30
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //SwipeRefresh
    implementation("androidx.compose.material:material")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:3.0.3")
    implementation("androidx.compose.ui:ui-test-junit4-android:1.7.8")
    testImplementation("com.google.dagger:hilt-android-testing:2.48")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.0.0-rc01")


    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("androidx.core:core-ktx:1.12.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")// или новее
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")

    // Moshi
    implementation("com.squareup.moshi:moshi:1.15.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")


    //Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //worker
    implementation("androidx.work:work-runtime-ktx:2.8.1")


// Тестовые зависимости для Android тестов
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.7")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")

// Обычные тестовые зависимости
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

// KAPT для Hilt в Android тестах
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.48")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    val mockkVersion = "1.13.10"
    testImplementation("io.mockk:mockk:${mockkVersion}")
    androidTestImplementation("io.mockk:mockk-android:${mockkVersion}")
    androidTestImplementation("io.mockk:mockk-agent:${mockkVersion}")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}