plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("com.google.dagger.hilt.android")
    id ("app.cash.paparazzi")
    kotlin("kapt")
}

android {
    namespace = "com.example.weldy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weldy"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.weldy.di.CustomTestRunner"
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
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val roomVersion = "2.6.1"
    val paging_version = "3.3.2"

    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("androidx.arch.core:core-testing:2.2.0") // Replace with your desired version
    testImplementation("com.google.truth:truth:1.4.4")

    testImplementation  ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation(platform("androidx.compose:compose-bom:2024.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation ("androidx.paging:paging-runtime-ktx:$paging_version")
    implementation("androidx.paging:paging-compose:$paging_version")
    testImplementation ("androidx.paging:paging-common:$paging_version")
    androidTestImplementation ("androidx.paging:paging-runtime:$paging_version")
    androidTestImplementation ("androidx.paging:paging-testing:$paging_version")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.10")

    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))
    implementation ("com.squareup.okhttp3:okhttp")

    implementation ("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    // For Robolectric tests.
    testImplementation ("com.google.dagger:hilt-android-testing:2.48")
    // ...with Kotlin.
    kaptTest ("com.google.dagger:hilt-android-compiler:2.48")

    // For instrumented tests.
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.48")
    // ...with Kotlin.
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.48")


    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    androidTestImplementation ("androidx.room:room-testing:$roomVersion")

    implementation("io.coil-kt:coil-compose:2.7.0")
    testImplementation("io.coil-kt:coil-test:2.7.0")

}