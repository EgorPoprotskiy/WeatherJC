package com.egorpoprotskiy.weatherjc

import com.egorpoprotskiy.weatherjc.api.RetrofitClient
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepository
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepositoryImpl

//10  Это будет "мозг" нашего приложения, который будет создавать и хранить все зависимости.
class AppContainer {
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(weatherApi = RetrofitClient.weatherApi)
    }
}