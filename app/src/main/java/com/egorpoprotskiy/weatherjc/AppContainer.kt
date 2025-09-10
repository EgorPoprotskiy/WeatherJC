package com.egorpoprotskiy.weatherjc

import com.egorpoprotskiy.weatherjc.api.RetrofitClient
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepository
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepositoryImpl

//10  Это будет "мозг" нашего приложения, который будет создавать и хранить все зависимости.
class AppContainer {
    // Константы для API-ключа и единиц измерения
    private val appid = BuildConfig.OPEN_WEATHER_MAP_API_KEY
    private val units = "metric"
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            weatherApi = RetrofitClient.weatherApi,
            appid = appid,
            units = units,
        )
    }
}