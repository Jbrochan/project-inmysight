plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.inmysight"
    compileSdkVersion(32)

    defaultConfig {
        applicationId = "com.example.inmysight"
        minSdk = 21
        targetSdk = 32
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding{
        android.buildFeatures.viewBinding = true
    }
}

dependencies {

    implementation(AndroidXDeps.CORE_KTX)
    implementation(AndroidXDeps.APPCOMPAT)
    implementation(AndroidXDeps.RECYCLERVIEW)
    implementation(AndroidXDeps.CONSTRAINTLAYOUT)

    testImplementation(TestDeps.JUNIT)
    androidTestImplementation(TestDeps.JUNIT_EXT)
    androidTestImplementation(TestDeps.ESPRESSO_CORE)

    implementation(GoogleDeps.MATERIAL)

    implementation(enforcedPlatform(Firebase.FIREBASE_BOM))
    implementation(Firebase.FIREBASE_FIRESTORE)
    implementation(Firebase.FIREBASE_ANALYTICS)
}