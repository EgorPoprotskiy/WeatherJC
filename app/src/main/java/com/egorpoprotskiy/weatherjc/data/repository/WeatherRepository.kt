package com.egorpoprotskiy.weatherjc.data.repository

import com.egorpoprotskiy.weatherjc.data.ForecastDto
import com.egorpoprotskiy.weatherjc.data.WeatherDto

//5
interface WeatherRepository {
    suspend fun getWeather(
        q: String
    ): WeatherDto

    //13
    suspend fun getWeatherForecast(
        city: String
    ) : ForecastDto
}