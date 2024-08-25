import org.jetbrains.kotlin.konan.properties.Properties


plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)

    //build config
    alias(libs.plugins.config.build)
}

android {
    namespace = "com.example.yourinteresests.android"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.example.yourinteresests.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }


    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    val accessToken = properties.getProperty("ACCESS_TOKEN_MAP")


    buildConfig {
        buildConfigField("String", "ACCESS_TOKEN_MAP", "\"$accessToken\"")
    }

    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.bottom.navigation)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.mapbox.compose)
    implementation(libs.mapbox.android)

    //koin
    implementation(libs.koin.androidx.core)
    implementation(libs.koin.android)


    //coil imagem
    implementation(libs.coil.compose)


    //loading
    implementation(libs.loading.compose)

    //animation
    implementation(libs.compose.animation)
}