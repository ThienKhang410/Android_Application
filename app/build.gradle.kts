plugins {
    alias(libs.plugins.android.application)

}

android {
    namespace = "com.example.lab5_web"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lab5_web"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform(libs.firebase.bom))
    implementation(platform(libs.firebase.bom)) // Đảm bảo sử dụng đúng alias từ libs.versions.toml
    implementation(libs.firebase.database)     // Thêm các thư viện Firebase khác (nếu cần)
    implementation(libs.firebase.auth)         // Ví dụ: Firebase Authentication
    implementation("com.google.firebase:firebase-database:20.2.2")

}

apply(plugin = "com.google.gms.google-services")

