plugins {
    id("com.android.application")

    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")

    id("io.fabric")

    id("androidx.navigation.safeargs")
}

repositories {
    google()
    jcenter()
    maven(url = "https://maven.fabric.io/public")
    maven(url = "https://jitpack.io")
    maven(url = "http://oss.sonatype.org/content/repositories/snapshots")
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
    mapDiagnosticLocations = true
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("moshi.generated", "javax.annotation.Generated")
    }
    javacOptions {
        option("-Xmaxerrs", 1000)
    }
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.skoumal.teanity.example"
        minSdkVersion(16)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    dataBinding {
        isEnabled = true
    }
}

dependencies {
    val versions: Map<String, String> by rootProject.extra

    implementation(project(":teanity"))

    // TESTING
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")

    implementation("com.crashlytics.sdk.android:crashlytics:${versions["crashlytics"]}@aar") {
        isTransitive = true
    }
    implementation("com.facebook.stetho:stetho:1.5.0")

    // KOTLIN
    implementation(kotlin("stdlib-jdk7", version = versions["kotlin"]))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions["coroutines"]}")

    // SUPPORT LIBS
    implementation("com.google.android.material:material:${versions["material"]}")
    implementation("androidx.constraintlayout:constraintlayout:${versions["constraintLayout"]}")

    // DEPENDENCY INJECTION
    implementation("org.koin:koin-core:${versions["koin"]}")
    implementation("org.koin:koin-android:${versions["koin"]}")
    implementation("org.koin:koin-androidx-viewmodel:${versions["koin"]}")

    // NETWORKING
    implementation("com.squareup.retrofit2:retrofit:${versions["retrofit"]}")
    implementation("com.squareup.retrofit2:converter-moshi:${versions["retrofit"]}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${versions["retrofit-coroutines"]}")
    implementation("com.squareup.okhttp3:okhttp:${versions["okhttp"]}")
    implementation("com.squareup.okhttp3:logging-interceptor:${versions["okhttp"]}")
    implementation("com.squareup.moshi:moshi:${versions["moshi"]}")

    // GLIDE
    implementation("com.github.bumptech.glide:okhttp3-integration:${versions["glide"]}@aar")

    // EASING
    implementation("com.chibatching.kotpref:kotpref:${versions["kotpref"]}")
    implementation("com.jakewharton.timber:timber:${versions["timber"]}")

    // KAPTs
    kapt("com.squareup.moshi:moshi-kotlin-codegen:${versions["moshi"]}")
    kapt("com.github.bumptech.glide:compiler:${versions["glide"]}")
    kapt("com.evernote:android-state-processor:${versions["state"]}")
    kapt("androidx.room:room-compiler:${versions["room"]}")
}
