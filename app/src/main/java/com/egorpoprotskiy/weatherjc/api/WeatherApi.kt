package com.egorpoprotskiy.weatherjc.api

import com.egorpoprotskiy.weatherjc.data.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

//3

interface WeatherApi {
    /**
     * @GET - это аннотация Retrofit, которая указывает, что этот метод будет выполнять GET-запрос.
     * "data/2.5/weather" - это путь к API, который будет добавлен к базовому URL.
     */
    @GET(value = "data/2.5/weather")
    suspend fun getWeather(
        /**
         * @Query - добавляет параметры запроса в URL.
         * Например, в URL будет добавлено "?q=London&appid=YOUR_API_KEY&units=metric".
         * @param q Название города.
         * @param appid Ваш API-ключ.
         * @param units Единицы измерения (например, "metric").
         */
        @Query("q") q: String,
        @Query("appid") appid: String,
        @Query("units") units: String = "metric"
    ): WeatherDto
}