package com.egorpoprotskiy.weatherjc.data.repository

import androidx.room.util.query
import com.egorpoprotskiy.weatherjc.api.WeatherApi
import com.egorpoprotskiy.weatherjc.data.ForecastDto
import com.egorpoprotskiy.weatherjc.data.WeatherDto
import java.net.UnknownHostException

//6

// Этот класс реализует интерфейс WeatherRepository.
// Он принимает наш сетевой сервис (WeatherApi) в качестве зависимости.
class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val appid: String,
    private val units: String
): WeatherRepository {
    // Внутри этого метода мы вызываем метод из нашего сетевого сервиса.
    override suspend fun getWeather(q: String): WeatherDto {
        // Мы просто передаем вызов в наш сетевой сервис.
        return try {
            weatherApi.getWeather(
                q = q,
                appid = appid,
                units = units
            )
        } catch (e: UnknownHostException) {
            throw IllegalStateException("Нет подключения к интернету")
        }
    }

    //14
    override suspend fun getWeatherForecast(city: String): ForecastDto {
        return try {
            weatherApi.getWeatherForecast(
                q = city,
                appid = appid,
                units = units
            )
        } catch (e: UnknownHostException) {
            throw IllegalStateException("Нет подключения к интернету")
        }
    }
}