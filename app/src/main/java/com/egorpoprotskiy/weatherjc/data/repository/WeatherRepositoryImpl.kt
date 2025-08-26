package com.egorpoprotskiy.weatherjc.data.repository

import com.egorpoprotskiy.weatherjc.api.WeatherApi
import com.egorpoprotskiy.weatherjc.data.WeatherDto

//6

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
): WeatherRepository {
    override suspend fun getWeather(
        q: String,
        appid: String,
        units: String
    ): WeatherDto {
        return weatherApi.getWeather(
            q = q,
            appid = appid,
            units = units
        )
    }
}