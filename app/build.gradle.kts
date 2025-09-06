import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //Kotlinx Serialization
    alias(libs.plugins.kotlin.serialization)
//    kotlin("plugin.serialization") version "2.1.20" - старый синтаксис
    //Room
    alias(libs.plugins.ksp)
    // Добавляем плагин Hilt
//    alias(libs.plugins.hilt.android) //используется KSP вместо KAPT
//    id("org.jetbrains.kotlin.kapt") //Устаревшая технология
//    id("com.google.dagger.hilt.android") // Плагин Hilt
}

val properties = Properties().apply {
    file(rootProject.rootDir.absolutePath + "/local.properties").inputStream().use { load(it) }
}

android {
    namespace = "com.egorpoprotskiy.weatherjc"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.egorpoprotskiy.weatherjc"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Добавляем поле для API-ключа, которое будет сгенерировано в BuildConfig.
        buildConfigField("String", "OPEN_WEATHER_MAP_API_KEY", properties.getProperty("OPEN_WEATHER_MAP_API_KEY"))
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
        // Включаем генерацию BuildConfig
        buildConfig = true
        compose = true
    }
}

dependencies {
    // AndroidX & Kotlin
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.compose.runtime.livedata)
    // Тесты
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //Kotlinx Serialization
    // Навигация
    implementation(libs.androidx.navigation.compose)
    //Для свайпа
    implementation(libs.androidx.compose.material)
    // Корутины
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    // Retrofit и Serialisation
//    Все зависимости закомментированы, потому что Мы используем libs.bundles.network.deps
//    implementation(libs.retrofit)
//    implementation(libs.retrofit.serialization)
//    implementation(libs.retrofit.gson)
//    implementation(libs.kotlinx.serialization.json)
//    implementation(libs.okhttp.logging.interceptor)
    implementation(platform(libs.okhttp.bom)) // Важно для BOM
    // Если ты используешь бандл:
    implementation(libs.bundles.network.deps)

    // Room (база данных)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Coil (загрузка изображений)
    implementation(libs.coil.compose)



    //Hilt
//    implementation(libs.hilt.android)
//    ksp(libs.hilt.compiler)
}