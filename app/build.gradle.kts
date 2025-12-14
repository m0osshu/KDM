plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
    id("com.google.gms.google-services")
    id("kotlin-kapt")

}

android {
    namespace = "com.example.myautoo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myautoo"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
    buildToolsVersion = "35.0.0"

    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/NOTICE.md",
                "META-INF/NOTICE",
                "META-INF/LICENSE"
            )
        }
    }
}


dependencies {
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-auth")

    // Compose (usando BOM, sin versiones explícitas)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation("com.google.android.material:material:1.13.0") // Tema XML de Material
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation("androidx.compose.material:material-icons-extended")

    // Navegación & ViewModel Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    implementation("androidx.navigation:navigation-compose:2.9.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")

    // ConstraintLayout Compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")

    // Coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.androidx.foundation.layout)
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Tests
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation("androidx.navigation:navigation-testing:2.9.3")
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
    testImplementation("org.mockito:mockito-core:5.3.1")

    //Mock
    testImplementation("io.mockk:mockk:1.13.10")
    androidTestImplementation("io.mockk:mockk-android:1.13.10")


    //Retrofit y Gson Converter
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    testImplementation(kotlin("test"))
}

tasks.withType<Test>().configureEach{
    useJUnitPlatform()
}