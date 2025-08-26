package com.egorpoprotskiy.weatherjc.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

//4

//Oбъект-синглтон для настройки и предоставления клиента Retrofit.
object RetrofitClient {
    // Базовый URL для OpenWeatherMap API
    private const val BASE_URL = "https://api.openweathermap.org/"
    // Создаём экземпляр kotlinx.serialization.Json для преобразования JSON в объекты Kotlin
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
    // Создаём экземпляр Retrofit
    private val retrofit: Retrofit by lazy {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // Добавляем конвертер для работы с kotlinx.serialization
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
    // Предоставляем доступ к нашему интерфейсу WeatherApi
    val weatherApi: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}