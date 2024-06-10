plugins {
    kotlin("kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "nam.tran.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(project(":core:common"))

    val hilt_version = "2.51.1"
    val retrofit_version = "2.9.0"
    val coroutine_version = "1.8.0"

    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    implementation("androidx.paging:paging-runtime-ktx:3.3.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutine_version")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.retrofit2:converter-scalars:$retrofit_version")

    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation("junit:junit:4.13.2")

    testImplementation("org.mockito:mockito-core:5.6.0")
    testImplementation("org.robolectric:robolectric:4.11.1")
    testImplementation("com.squareup.okhttp3:mock webserver:4.0.1")
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")
    testImplementation("com.nhaarman:mockito-kotlin:1.5.0")
    testImplementation("org.mockito:mockito-inline:3.11.2")
}