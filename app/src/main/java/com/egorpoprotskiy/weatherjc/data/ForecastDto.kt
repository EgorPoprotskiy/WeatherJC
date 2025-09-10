package com.egorpoprotskiy.weatherjc.data

import kotlinx.serialization.Serializable

@Serializable
data class ForecastDto (
    val list: List<ForecastItem>,
    val city: City,
    val cod: String,
    val message: Int,
    val cnt: Int
)

@Serializable
data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
)

@Serializable
data class ForecastItem(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: ForecastSys,
    val dt_txt: String
)

@Serializable
data class ForecastSys(
    val pod: String
)
