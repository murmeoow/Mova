plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "dm.sample.mova"
    compileSdk = 33

    defaultConfig {
        applicationId = "dm.sample.mova"
        minSdk = 23
        targetSdk = 33
        versionCode = 14
        versionName = "1.13"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String","YOUTUBE_API_KEY", project.property("YOUTUBE_API_KEY") as String)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.majorVersion
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
    implementation("androidx.appcompat:appcompat:1.5.1")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    implementation( project(":data") )
    implementation( project(":domain") )

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.fragment:fragment-ktx:1.5.4")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")

    // Compose
    implementation("androidx.compose.ui:ui:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.1")
    implementation("androidx.compose.material:material:1.1.1")
    implementation("androidx.compose.runtime:runtime:1.1.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.1.1")
    implementation("com.google.accompanist:accompanist-pager:0.27.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.27.0")
    implementation ("com.google.accompanist:accompanist-webview:0.27.0")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    //==================== Dependency Injection ====================
    val hiltVersion = "2.44"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.5.0")

    // Constraint layout Compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    // Flow layout
    implementation("com.google.accompanist:accompanist-flowlayout:0.26.5-rc")

    debugImplementation("androidx.customview:customview:1.2.0-alpha01")
    debugImplementation("androidx.customview:customview-poolingcontainer:1.0.0-alpha01")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.1.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.1")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // Biometric
    implementation("androidx.biometric:biometric:1.1.0")

    // Shimmer
    implementation("com.valentinilk.shimmer:compose-shimmer:1.0.3")

    // Showkase
    implementation("com.airbnb.android:showkase:1.0.0-beta14")
    kapt("com.airbnb.android:showkase-processor:1.0.0-beta14")

    // SplashScreen API
    implementation("androidx.core:core-splashscreen:1.0.0")

    //Color Palette
    implementation("androidx.palette:palette-ktx:1.0.0")
}