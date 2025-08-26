package com.egorpoprotskiy.weatherjc.data

import android.media.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//DTO расшифровывается как Data Transfer Object — объект для передачи данных.
@Serializable
data class WeatherDto (
    val name: String,
    @SerialName("coord")
    val coord: Coord,
    @SerialName("weather")
    val weather: List<Weather>,
    @SerialName("main")
    val main: Main,
    @SerialName("wind")
    val wind: Wind,
    @SerialName("clouds")
    val clouds: Clouds,
    @SerialName("sys")
    val sys: Sys
)
//Координаты
@Serializable
data class Coord (
    val lon: Double,
    val lat: Double
)

//Weather
@Serializable
data class Weather (
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

//Main
@Serializable
data class Main (
    val temp: Double,
    @SerialName(value = "feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int
)

//Wind
@Serializable
data class Wind (
    val speed: Double,
    val deg: Int
)

//Cloud
@Serializable
data class Clouds (
    val all: Int
)

//Sys
@Serializable
data class Sys (
    val country: String,
    val sunrise: Long,
    val sunset: Long
)