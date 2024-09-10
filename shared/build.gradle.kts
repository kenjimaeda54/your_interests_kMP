import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)


    //seriliation
    alias(libs.plugins.serialization)

    //touchlab para view model
    alias(libs.plugins.touchlab.skie)

    //build config
    alias(libs.plugins.config.build)

    //plugin sqldelight
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }


    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    val apikey = properties.getProperty("API_KEY")
    val supabaseKey = properties.getProperty("SUPABASE_KEY")
    
    buildConfig {
        buildConfigField("String", "API_KEY", "\"$apikey\"")
        buildConfigField("String", "SUPABASE_KEY", "\"$supabaseKey\"")
    }


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {

        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.coroutines.ktx)
            implementation(libs.compass.geocoder)
            implementation(libs.compass.geocoder.mobile)
            implementation(libs.compass.geolocation)
            implementation(libs.compass.geolocation.mobile)
            implementation(libs.koin.core)
            implementation(libs.ktor.negotiation)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.client.core)
            implementation(
                project.dependencies.platform(libs.superbase.bom))
            implementation(libs.superbase.gotrue)
            implementation(libs.superbase.realtime)
            implementation(libs.superbase.postgrest)
            implementation(libs.superbase.storage)
            implementation(libs.peekaboo.image)
            implementation(libs.peekaboo.image.picker)
            implementation(libs.sql.coroutines.extensions)
            //apesar de precisar deles para copiilar no ios
            //ao fazer o make project no android stuido vai falhar
            //porque nao va encontrar o modulo no ios,porem e necessario
            implementation(libs.touchlab.stately.common)
        }

        androidMain.dependencies {
            implementation(libs.viewModel.ktx)
            implementation(libs.koin.android)
            implementation(libs.ktor.client.android)
            implementation(libs.sql.android.driver)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sql.native.driver)
            //apesar de precisar deles para copiilar no ios
            //ao fazer o make project no android stuido vai falhar
            //porque nao va encontrar o modulo no ios,porem e necessario
            implementation(libs.touchlab.stately.isolate)
            implementation(libs.touchlab.stately.common)

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.yourinteresests"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


sqldelight {
    databases {
        create("InterestsDB") {
            packageName = "com.example.yourinterest.db"
        }
    }
}
