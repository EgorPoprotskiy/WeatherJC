package com.egorpoprotskiy.weatherjc.presentation.viewmodel

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepository

//9


class WeatherViewModel(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    suspend fun getWeather() {

    }
}