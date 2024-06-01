plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("kotlin-kapt")
}

android {
  namespace = "com.example.shoppinglist"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.shoppinglist"
    minSdk = 24
    targetSdk = 34
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
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs = ['-Xjvm-default=compatibility']
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.1.0-beta01"
  }
}

dependencies {

  implementation("androidx.compose.material3:material3-android:1.2.1")
  dependencies {

    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.compose.ui:ui:1.1.0-beta01")
    implementation ("androidx.compose.material:material:1.1.0-beta01")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.1.0-beta01")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation ("androidx.activity:activity-compose:1.3")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.1.0-beta01")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.1.0-beta01")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.1.0-beta01")
    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.5.0")
    // compose viewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    //room
    implementation ("androidx.room:room-runtime:2.4.2")
    implementation ("androidx.room:room-ktx:2.4.2")
    kapt ("androidx.room:room-compiler:2.4.2")
    testImplementation ("androidx.room:room-testing:2.4.2")
    androidTestImplementation ("androidx.room:room-testing:2.4.2")
  }
}