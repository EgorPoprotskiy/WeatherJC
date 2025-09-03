package com.egorpoprotskiy.weatherjc.data.repository

import com.egorpoprotskiy.weatherjc.api.WeatherApi
import com.egorpoprotskiy.weatherjc.data.WeatherDto

//6

// Этот класс реализует интерфейс WeatherRepository.
// Он принимает наш сетевой сервис (WeatherApi) в качестве зависимости.
class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
): WeatherRepository {
    // Внутри этого метода мы вызываем метод из нашего сетевого сервиса.
    override suspend fun getWeather(
        q: String,
        appid: String,
        units: String
    ): WeatherDto {
        // Мы просто передаем вызов в наш сетевой сервис.
        return weatherApi.getWeather(
            q = q,
            appid = appid,
            units = units
        )
    }
}