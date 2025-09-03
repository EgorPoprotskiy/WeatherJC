package com.egorpoprotskiy.weatherjc

import com.egorpoprotskiy.weatherjc.api.WeatherApi
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepository
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

//10  Это будет "мозг" нашего приложения, который будет создавать и хранить все зависимости.
class AppContainer {
    private val baseUrl = "https://api.openweathermap.org"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()
    private val weatherApi: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(weatherApi = weatherApi)
    }
}