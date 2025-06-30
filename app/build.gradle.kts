plugins {
    alias(libs.plugins.android.application)
    id("androidx.navigation.safeargs")
}

android {
    namespace = "edu.esiea.orienteering"
    compileSdk = 35

    defaultConfig {
        applicationId = "edu.esiea.orienteering"
        minSdk = 34
        //noinspection OldTargetApi
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.dynamic.features.fragment)
    implementation(libs.kotlinx.serialization.json)

    // room
    implementation(libs.room.runtime)
    implementation(libs.androidx.room.common.jvm)
    annotationProcessor(libs.room.compiler)

    // lifecycle
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.9.1")

    // junit
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.espresso.core)

    // osm
    implementation(libs.osmdroid.android)

    //holorcolorpicker
    implementation(libs.holocolorpicker)
}