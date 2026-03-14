plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.objectbox)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.ai.assistance.operit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ai.assistance.operit"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.9.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/main/AndroidManifest.xml")
            java.srcDirs("src/main/java", "src/main/kotlin")
            res.srcDirs("src/main/res")
            assets.srcDirs("src/main/assets")
        }
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.desugar.jdk)

    implementation(libs.okhttp)
    implementation(libs.okhttp.sse)
    implementation(libs.jsoup)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.objectbox.kotlin)
    kapt(libs.objectbox.processor)

    implementation(libs.navigation.compose)

    implementation(libs.datastore.preferences)
    implementation(libs.datastore.preferences.core)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation(libs.work.runtime.ktx)

    implementation(libs.coil)
    implementation(libs.coil.compose)

    implementation(libs.kotlinx.serialization)
    implementation(libs.gson)

    implementation(libs.exoplayer)
    implementation(libs.exoplayer.core)
    implementation(libs.exoplayer.ui)

    implementation(libs.material3.window)
    implementation(libs.window)

    implementation(libs.reorderable)
    implementation(libs.swipe)

    implementation(libs.colorpicker)

    implementation(libs.nanohttpd)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}