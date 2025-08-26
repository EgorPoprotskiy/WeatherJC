package com.egorpoprotskiy.weatherjc.data.repository

import com.egorpoprotskiy.weatherjc.data.WeatherDto

//5
interface WeatherRepository {
    suspend fun getWeather(
        q: String,
        appid: String,
        units: String
    ): WeatherDto
}